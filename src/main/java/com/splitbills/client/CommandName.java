package com.splitbills.client;

public enum CommandName {
    LOGIN("login"),
    REGISTER("register"),
    ADD_FRIEND("add-friend"),
    ADD_GROUP("add-group"),
    SPLIT("split"),
    SPLIT_GROUP("split-group"),
    GET_STATUS("get-status"),
    EXIT("exit");
    private String name;

    CommandName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
