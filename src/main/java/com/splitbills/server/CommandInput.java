package com.splitbills.server;

public class CommandInput {

    private String clientToken;
    private String commandName;
    private String[] commandArguments;

    public String getClientToken() {
        return clientToken;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getCommandArguments() {
        return commandArguments;
    }

}
