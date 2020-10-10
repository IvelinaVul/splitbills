package com.splitbills.commands;

public enum Response {
    OK("ok"),
    ALREADY_EXISTS("already-exists"),
    INVALID_ARGUMENTS("invalid-arguments"),
    SERVER_ERROR("server-error");
    private String message;

    Response(String message) {
        this.message = message;
    }

    }
