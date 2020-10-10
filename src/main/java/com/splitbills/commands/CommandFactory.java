package com.splitbills.commands;

public class CommandFactory {

    public Command getCommand(String inputName) throws InvalidArgumentsException {
        inputName = inputName.toLowerCase().trim();
        CommandName commandName = CommandName.valueOf(inputName);
        Command command;
        switch (commandName) {
            case REGISTER:
                command = new Register();
                break;
            default:
                throw new InvalidArgumentsException("There is no command with the given name");

        }
        ;
        return command;
    }

}
