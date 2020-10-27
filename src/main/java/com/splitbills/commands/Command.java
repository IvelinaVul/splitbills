package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Command {

    protected UserRepository userRepository;
    protected GroupRepository groupRepository;
    protected Map<String, String> loggedInUsers;

    Command(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.loggedInUsers = new HashMap<>();
    }

    public abstract Result execute(List<String> arguments, String token);

}
