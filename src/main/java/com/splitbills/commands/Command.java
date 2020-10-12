package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.GroupRepositoryImpl;
import com.splitbills.database.UserRepository;
import com.splitbills.database.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Command {

    protected UserRepository userRepository;
    protected GroupRepository groupRepository;
    protected Map<String, String> loggedInUsers;

    public Command(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.loggedInUsers = new HashMap<>();
    }

    public abstract Result execute(List<String> arguments, String token);

}
