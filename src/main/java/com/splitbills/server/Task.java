package com.splitbills.server;

import java.nio.channels.SocketChannel;

public class Task {

    private CommandInput commandInput;
    private SocketChannel socketChannel;
    private String response;

    public CommandInput getCommandInput() {
        return commandInput;
    }

    public void setCommandInput(CommandInput commandInput) {
        this.commandInput = commandInput;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
