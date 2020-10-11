package com.splitbills.commands;

import com.splitbills.account.HashingException;
import com.splitbills.account.Password;
import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.User;

import java.util.List;

public class Register extends Command {

    public Register() {
        super();
    }

    public Register(UserRepository userRepository, GroupRepository groupRepository) {
        super(userRepository, groupRepository);
    }

    @Override
    public Status execute(List<String> arguments) {
        if (!hasExpectedArguments(arguments)) {
            return Status.INVALID_ARGUMENTS;
        }
        String username = arguments.get(0);
        String password = arguments.get(1);
        if (!hasValidCredentials(username, password)) {
            return Status.INVALID_ARGUMENTS;
        }
        if (userRepository.contains(username)) {
            return Status.ALREADY_EXISTS;
        }

        Status response = Status.OK;
        try {
            register(username, password.toCharArray());
        } catch (HashingException hashingException) {
            response = Status.SERVER_ERROR;
        }
        return response;
    }

    private boolean hasExpectedArguments(List<String> arguments) {
        int expectedArguments = 2;

        return arguments != null && arguments.size() == expectedArguments;

    }

    private boolean hasValidCredentials(String username, String password) {
        return (username != null && password != null);

    }

    private void register(String username, char[] password) throws HashingException {
        byte[] salt = Password.generateSalt();
        byte[] hashedPassword = Password.getHash(password, salt);
        User user = new User(username, hashedPassword, salt);
        userRepository.add(user);
    }
}
