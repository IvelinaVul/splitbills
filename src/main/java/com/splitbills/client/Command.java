package com.splitbills.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Command {

    @SerializedName(value = "clientToken")
    private String authenticationToken;

    public String getName() {
        return name;
    }

    @SerializedName(value = "commandName")
    private String name;
    @SerializedName(value = "commandArguments")
    private List<String> arguments;

    public Command(String name, List<String> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
