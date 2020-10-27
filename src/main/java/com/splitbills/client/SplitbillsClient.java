package com.splitbills.client;

import com.google.gson.Gson;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SplitbillsClient implements AutoCloseable {

    private final static Logger LOGGER = Logger.getLogger(SplitbillsClient.class.getName());
    private final static int SERVER_PORT = 1515;
    private final static String SERVER_HOST = "localhost";
    private final ByteBuffer readBuffer;
    private final Gson gson = new Gson();
    private SocketChannel socketChannel;

    public SplitbillsClient() {
        int bufferSize = 8192;
        this.readBuffer = ByteBuffer.allocate(bufferSize);
    }

    public Response sendCommand(Command command) throws IOException {
        if (!isConnectedToServer()) {
            connectToServer();
        }
        sendToServer(command);
        return getServerResponse();
    }

    private boolean isConnectedToServer() {
        return socketChannel != null && socketChannel.isConnected();
    }

    private void connectToServer() throws DisconnectedException {
        try {
            if (socketChannel == null || !socketChannel.isOpen()) {
                socketChannel = SocketChannel.open();
            }
            SocketAddress socketAddress = new InetSocketAddress(SERVER_HOST, SERVER_PORT);
            socketChannel.connect(socketAddress);
            LOGGER.log(Level.INFO, "Connected to server");

        } catch (IOException ioException) {
            LOGGER.log(Level.ERROR, "Cannot connect to server", ioException);
            throw new DisconnectedException(ioException);
        }
    }

    private void sendToServer(Command command) throws IOException {
        String json = gson.toJson(command);
        ByteBuffer buffer = ByteBuffer.wrap(json.getBytes());
        if (socketChannel.isConnected()) {
            socketChannel.write(buffer);
        }
    }

    private Response getServerResponse() throws IOException {
        if (!socketChannel.isConnected()) {
            LOGGER.log(Level.ERROR, "No connection to server");
            throw new DisconnectedException();
        }
        Response response = null;
        String reply = getServerReply();
        if (reply != null) {
            response = gson.fromJson(reply, Response.class);
        }
        return response;

    }

    private String getServerReply() throws IOException {
        String reply = null;
        int bytesRead;
        readBuffer.clear();
        bytesRead = socketChannel.read(readBuffer);

        if (bytesRead > 0 && bytesRead <= readBuffer.limit()) {
            reply = new String(readBuffer.array(), 0, bytesRead);
        }
        return reply;
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

}
