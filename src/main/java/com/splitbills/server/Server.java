package com.splitbills.server;

import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
    private final static int DEFAULT_PORT = 1515;
    private final static String DEFAULT_HOST = "localhost";
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final EventDistributor eventDistributor;
    private final EventProcessor eventProcessor;

    public Server() throws IOException {
        this(InetAddress.getByName(DEFAULT_HOST), DEFAULT_PORT);
    }

    public Server(InetAddress address, int port) throws IOException {
        serverSocketChannel = initializeServerChannel(address, port);
        selector = initializeSelector(serverSocketChannel, SelectionKey.OP_ACCEPT);

        BlockingQueue<Task> newTasks = new LinkedBlockingQueue<>();
        BlockingQueue<Task> completedTasks = new LinkedBlockingQueue<>();
        eventDistributor = new EventDistributor(selector, newTasks, completedTasks);
        eventProcessor = new EventProcessor(newTasks, completedTasks);
    }

    private ServerSocketChannel initializeServerChannel(InetAddress inetAddress, int port) throws IOException {
        SocketAddress address = new InetSocketAddress(inetAddress, port);
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(address);
        return serverChannel;
    }

    private Selector initializeSelector(ServerSocketChannel serverChannel, int selectEvents) throws IOException {
        Selector selector = Selector.open();
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, selectEvents);
        return selector;
    }

    private void start() {
        new Thread(eventDistributor).start();
        new Thread(eventProcessor).start();
        LOGGER.log(Level.INFO, "Server started");
    }

    private void stop() {
        eventProcessor.stop();
        eventDistributor.stop();
        try {
            serverSocketChannel.close();
        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "Error when closing Server Socket Channel", ioException);
        }
        try {
            selector.close();
        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "Error when closing Selector", ioException);
        }
        LOGGER.log(Level.INFO, "Server stopped");
    }


}
