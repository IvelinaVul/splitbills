package com.splitbills.commands;

import java.util.Arrays;

public enum CommandName {
    LOGIN("login"),
    REGISTER("register"),
    ADD_FRIEND("add-friend"),
    ADD_GROUP("add-group"),
    SPLIT("split"),
    SPLIT_GROUP("split-group"),
    GET_STATUS("get-status");
    private final String name;

    CommandName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CommandName getCommandName(String commandName) throws InvalidCommandException {
        return Arrays.stream(values())
                .filter(c -> c.name.equalsIgnoreCase(commandName))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException("No such command"));
    }

}
