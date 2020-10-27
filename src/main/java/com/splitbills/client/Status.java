package com.splitbills.client;
public enum Status {
    OK("ok"),
    DISCONNECTED("disconnected"),
    ALREADY_EXISTS("already-exists"),
    INVALID_ARGUMENTS("invalid-arguments"),
    SERVER_ERROR("server-error"),
    NOT_LOGGED_IN("not-logged-in"),
    ALREADY_LOGGED_IN("already-logged-in"),
    NOT_EXISTING("not-existing"),
    NOT_REGISTERED("not-registered"),
    INVALID_COMMAND("invalid-command");
    private String message;

    Status(String message) {
        this.message = message;
    }

}
