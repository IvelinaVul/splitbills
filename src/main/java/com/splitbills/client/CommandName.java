package com.splitbills.client;

import com.splitbills.commands.InvalidCommandException;

import java.util.Arrays;

public enum CommandName {
    LOGIN("login"),
    REGISTER("register"),
    ADD_FRIEND("add-friend"),
    ADD_GROUP("add-group"),
    SPLIT("split"),
    SPLIT_GROUP("split-group"),
    GET_STATUS("get-status"),
    EXIT("exit"),
    NO_SUCH_COMMAND_NAME("no-such-command-name");
    private String name;

    CommandName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CommandName getCommandName(String commandName)  {
        return Arrays.stream(values())
                .filter(c -> c.name.equalsIgnoreCase(commandName))
                .findFirst()
                .orElse(NO_SUCH_COMMAND_NAME);
    }
}
