package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private Map<String,String> loggedInUsers;

    public CommandFactory(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.loggedInUsers=new HashMap<>();
    }

    public Command getCommand(String inputName) throws InvalidCommandException {
        inputName = inputName.toUpperCase().trim();
        CommandName commandName = CommandName.getCommandName(inputName);
        switch (commandName) {
            case LOGIN:
                return new Login(userRepository, groupRepository,loggedInUsers);
            case REGISTER:
                return new Register(userRepository, groupRepository,loggedInUsers);
            case ADD_GROUP:
                return new AddGroup(userRepository, groupRepository,loggedInUsers);
            case SPLIT_GROUP:
                return new SplitGroup(userRepository,groupRepository,loggedInUsers);
        }
        throw new InvalidCommandException("No such command");
    }

}
