package com.splitbills.commands;

public enum Status {
    OK("ok"),
    ALREADY_EXISTS("already-exists"),
    INVALID_ARGUMENTS("invalid-arguments"),
    NOT_MATCHING_ARGUMENTS("not-matching"),
    SERVER_ERROR("server-error"),
    NOT_LOGGED_IN("not-logged-in"),
    ALREADY_LOGGED_IN("already-logged-in"),
    NOT_REGISTERED("not-registered"),
    INVALID_COMMAND("invalid-command");
    private String message;

    Status(String message) {
        this.message = message;
    }

}
