package com.splitbills.commands;

public enum Status {
    OK("ok"),
    ALREADY_EXISTS("already-exists"),
    INVALID_ARGUMENTS("invalid-arguments"),
    SERVER_ERROR("server-error"),
    NOT_LOGGED_IN("not-logged-in"),
    ALREADY_LOGGED_IN("already-logged-in"),
    NOT_EXISTING("not-existing"),
    NOT_REGISTERED("not-registered");
    private String message;

    Status(String message) {
        this.message = message;
    }

}
