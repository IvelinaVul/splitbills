package com.splitbills.commands;

import com.splitbills.account.HashingException;
import com.splitbills.account.Password;
import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserAlreadyExistsException;
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
        if (!isValid(arguments)) {
            return Status.INVALID_ARGUMENTS;
        }
        String username = arguments.get(0);
        String password = arguments.get(1);
        if (username == null || password == null) {
            return Status.INVALID_ARGUMENTS;
        }
        Status response = Status.OK;
        try {
            register(username, password.toCharArray());
        } catch (HashingException hashingException) {
            response = Status.SERVER_ERROR;
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            response = Status.ALREADY_EXISTS;
        }
        return response;
    }

    private boolean isValid(List<String> arguments) {
        int expectedArguments = 2;

        return arguments != null && arguments.size() == expectedArguments;
    }

    private void register(String username, char[] password) throws HashingException, UserAlreadyExistsException {
        byte[] salt = Password.generateSalt();
        byte[] hashedPassword = Password.getHash(password, salt);
        User user = new User(username, hashedPassword, salt);
        userRepository.add(user);
    }
}
