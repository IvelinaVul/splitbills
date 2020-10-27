package com.splitbills.server;

import com.google.gson.Gson;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static java.util.Collections.emptyIterator;

public class EventDistributor implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(EventDistributor.class.getName());
    private final BlockingQueue<Task> newTasks;
    private final BlockingQueue<Task> completedTasks;
    private final Selector selector;
    private boolean isRunning = true;
    private Map<SocketChannel, Queue<String>> readyResponses;
    private ByteBuffer readBuffer;
    private Gson gson;

    public EventDistributor(Selector selector, BlockingQueue<Task> newTasks, BlockingQueue<Task> completedTasks) {
        this.selector = selector;
        this.newTasks = newTasks;
        this.completedTasks = completedTasks;
        this.readyResponses = new HashMap<>();
        int bufferSize = 8192;
        this.readBuffer = ByteBuffer.allocate(bufferSize);
        this.gson = new Gson();
    }

    @Override
    public void run() {
        while (isRunning) {
            int maxTasks = 10;
            registerWriteEventsForCompletedTasks(maxTasks);
            distributeAllEvents();
        }
    }

    private void registerWriteEventsForCompletedTasks(int maxTasks) {
        List<Task> writeTasks = getNewTasks(maxTasks);
        for (Task task : writeTasks) {
            SocketChannel socketChannel = task.getSocketChannel();
            String response = task.getResponse();

            if (response != null) {
                try {
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                    addToWaiting(socketChannel, response);
                } catch (ClosedChannelException closedChannelException) {
                    LOGGER.log(Level.ERROR, "Remote closed the channel", closedChannelException);
                }
            }

        }
    }

    private List<Task> getNewTasks(int maxTasks) {
        List<Task> writeEvents = new ArrayList<>();
        completedTasks.drainTo(writeEvents, maxTasks);
        return writeEvents;
    }

    private void addToWaiting(SocketChannel socketChannel, String response) {
        Queue<String> queue;
        if (readyResponses.containsKey(socketChannel)) {
            queue = readyResponses.get(socketChannel);
        } else {
            queue = new ArrayDeque<>();
        }
        queue.add(response);
        readyResponses.put(socketChannel, queue);
    }

    private void distributeAllEvents() {
        Iterator<SelectionKey> selectedKeys = getSelectedKeys();
        while (selectedKeys.hasNext()) {
            SelectionKey key = selectedKeys.next();
            selectedKeys.remove();
            if (key.isValid()) {
                distribute(key);
            }
        }
    }

    private Iterator<SelectionKey> getSelectedKeys() {
        Iterator<SelectionKey> selectedKeysIterator;
        try {
            int timeout = 100;
            selector.select(timeout);
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            selectedKeysIterator = selectedKeys.iterator();
        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "No keys selected", ioException);
            selectedKeysIterator = emptyIterator();
        }
        return selectedKeysIterator;
    }

    private void distribute(SelectionKey key) {

        if (key.isAcceptable()) {
            accept(key);
        } else if (key.isReadable()) {
            createTask(key);
        } else if (key.isWritable()) {
            sendResponse(key);
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
        } catch (ClosedChannelException closedChannelException) {
            LOGGER.log(Level.ERROR, "Channel is closed", closedChannelException);
        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "Error while configuring channel in non blocking mode", ioException);
        }
    }

    private void createTask(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            CommandInput commandInput = getCommandInput(socketChannel);
            Task task = new Task();
            task.setCommandInput(commandInput);
            task.setSocketChannel(socketChannel);
            newTasks.add(task);
        } catch (ClosedChannelException closedChannelException) {
            LOGGER.log(Level.ERROR, "The remote closed the connection", closedChannelException);
            key.cancel();
            closeChannel(key.channel());
        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "Error when getting client input", ioException);
        }
    }

    private CommandInput getCommandInput(SocketChannel channel) throws IOException {
        CommandInput command;
        byte[] data = read(channel);
        String json = new String(data);
        command = gson.fromJson(json, CommandInput.class);
        return command;
    }

    private byte[] read(SocketChannel channel) throws IOException {
        int bytesRead;
        readBuffer.clear();
        bytesRead = channel.read(readBuffer);

        final int EOF = -1;
        if (bytesRead == EOF) {
            LOGGER.log(Level.WARN, "The remote closed the connection");
            throw new ClosedChannelException();
        }
        byte[] data = new byte[bytesRead];
        int startPosition = 0;
        int destinationStartPosition = 0;
        System.arraycopy(readBuffer.array(), startPosition, data, destinationStartPosition, bytesRead);
        return data;
    }

    private void sendResponse(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (readyResponses.containsKey(socketChannel)) {
            Queue<String> responses = readyResponses.get(socketChannel);
            if (!responses.isEmpty()) {
                String response = responses.poll();
                try {
                    socketChannel.write(ByteBuffer.wrap(response.getBytes()));
                } catch (IOException ioException) {
                    LOGGER.log(Level.ERROR, "Response not sent", ioException);
                }
            }
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    private void closeChannel(Channel channel) {
        try {
            channel.close();
        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "Cannot close channel");
        }
    }

    public void stop() {
        isRunning = false;
    }
}
