package com.splitbills.commands;

public class CommandFactory {

    public Command getCommand(String name) throws InvalidArgumentsException {
        name = name.toLowerCase().trim();
        CommandName command = CommandName.valueOf(name);
        return switch (command) {
            case REGISTER -> new Register();
            default -> throw new InvalidArgumentsException("There is no command with the given name");

        };
    }

}
