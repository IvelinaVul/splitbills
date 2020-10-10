package com.splitbills.commands;

public class CommandFactory {

    public Command getCommand(String inputName) throws InvalidCommandException {
        inputName = inputName.toLowerCase().trim();
        CommandName commandName = CommandName.valueOf(inputName);
        Command command;
        switch (commandName) {
            case REGISTER:
                command = new Register();
                break;
            case ADD_FRIEND:
                command = new AddFriend();
                break;
            case ADD_GROUP:
                command=new AddGroup();
                break;
            default:
                throw new InvalidCommandException("There is no command with the given name");
        }
        return command;
    }

}
