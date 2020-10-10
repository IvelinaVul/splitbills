package com.splitbills.server;

import java.util.List;

public class CommandInput {

    private String clientToken;
    private String commandName;
    private List<String> commandArguments;

    public String getClientToken() {
        return clientToken;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getCommandArguments() {
        return commandArguments;
    }

}
