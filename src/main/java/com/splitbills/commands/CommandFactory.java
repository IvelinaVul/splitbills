package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;

public class CommandFactory {

    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public CommandFactory(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Command getCommand(String inputName) throws InvalidCommandException {
        inputName = inputName.toUpperCase().trim();
        CommandName commandName = CommandName.getCommandName(inputName);
        switch (commandName) {
            case HELP:
                return new Help(userRepository, groupRepository);
            case LOGIN:
                return new Login(userRepository, groupRepository);
            case REGISTER:
                return new Register(userRepository, groupRepository);
            case ADD_GROUP:
                return new AddGroup(userRepository, groupRepository);
        }
        throw new InvalidCommandException("No such command");
    }

}
