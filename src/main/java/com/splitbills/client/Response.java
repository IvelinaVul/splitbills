package com.splitbills.client;

import java.util.List;

public class Response {

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    private Status status;
    private List<String> arguments;

    public Response(Status status, List<String> arguments) {
        this.status = status;
        this.arguments = arguments;
    }

    public Response(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getArguments() {
        return arguments;
    }

}
