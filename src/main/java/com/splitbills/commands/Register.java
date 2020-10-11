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
    public Result execute(List<String> arguments, String token) {
        if (!hasExpectedArguments(arguments)) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        String username = arguments.get(0);
        String password = arguments.get(1);
        if (!hasValidCredentials(username, password)) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        if (loggedInUsers.containsKey(username) || token != null) {
            return new Result(Status.ALREADY_LOGGED_IN);
        }
        if (userRepository.contains(username)) {
            return new Result(Status.ALREADY_EXISTS);
        }

        Status status = Status.OK;
        try {
            register(username, password.toCharArray());
        } catch (HashingException hashingException) {
            status = Status.SERVER_ERROR;
        }
        return new Result(status);
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
