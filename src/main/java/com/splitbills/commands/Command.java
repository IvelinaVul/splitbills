package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.GroupRepositoryImpl;
import com.splitbills.database.UserRepository;
import com.splitbills.database.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class Command {

    protected UserRepository userRepository;
    protected GroupRepository groupRepository;

    public Command() {
        String persistenceUnit = "account";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        this.userRepository = new UserRepositoryImpl(entityManagerFactory);
        this.groupRepository = new GroupRepositoryImpl(entityManagerFactory);
    }

    public Command(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public abstract Response execute(List<String> arguments) throws InvalidArgumentsException;

}
