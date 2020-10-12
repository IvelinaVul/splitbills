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
        inputName = inputName.toLowerCase().trim();
        CommandName commandName = CommandName.valueOf(inputName);
        Command command;
        switch (commandName) {
            case LOGIN:
                command = new Login(userRepository, groupRepository);
                break;
            case REGISTER:
                command = new Register(userRepository, groupRepository);
                break;
            case ADD_GROUP:
                command = new AddGroup(userRepository, groupRepository);
                break;
            default:
                throw new InvalidCommandException("There is no command with the given name");
        }
        return command;
    }

}
