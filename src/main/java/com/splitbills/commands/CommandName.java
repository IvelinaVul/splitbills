package com.splitbills.commands;

public enum CommandName {
    HELP("help"),
    LOGIN("login"),
    REGISTER("register"),
    ADD_FRIEND("add-friend"),
    ADD_GROUP("add-group"),
    SPLIT("split"),
    SPLIT_GROUP("split-group"),
    GET_STATUS("get-status");
    private String name;

    CommandName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
