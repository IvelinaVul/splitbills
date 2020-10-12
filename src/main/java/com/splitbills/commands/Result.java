package com.splitbills.commands;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private Status status;
    private List<String> arguments;

    public Result(Status status) {
        this(status, new ArrayList<>());
    }

    public Result(Status status, List<String> arguments) {
        this.status = status;
        this.arguments = arguments;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
