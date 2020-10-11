package com.splitbills.commands;

public class CommandFactory {

    public Command getCommand(String inputName) throws InvalidCommandException {
        inputName = inputName.toLowerCase().trim();
        CommandName commandName = CommandName.valueOf(inputName);
        Command command;
        switch (commandName) {
            case LOGIN:
                command = new Login();
                break;
            case REGISTER:
                command = new Register();
                break;
            default:
                throw new InvalidCommandException("There is no command with the given name");
        }
        return command;
    }

}
