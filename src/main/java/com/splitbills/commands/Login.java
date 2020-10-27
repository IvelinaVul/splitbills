package com.splitbills.commands;

import com.splitbills.account.HashingException;
import com.splitbills.account.Password;
import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Login extends Command {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    Login(UserRepository userRepository, GroupRepository groupRepository) {
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
        return login(username, password.toCharArray());
    }

    private boolean hasExpectedArguments(List<String> arguments) {
        int expectedArguments = 2;
        return arguments != null && arguments.size() == expectedArguments;
    }

    private boolean hasValidCredentials(String username, String password) {
        return (username != null && password != null);
    }

    private Result login(String username, char[] password) {
        try {
            if (!isCorrectPassword(username, password)) {
                return new Result(Status.INVALID_ARGUMENTS);
            }
        } catch (NoSuchUserException noSuchUserException) {
            return new Result(Status.NOT_EXISTING);
        } catch (HashingException hashingException) {
            return new Result(Status.SERVER_ERROR);
        }
        String token = generateAuthenticationToken();
        loggedInUsers.put(username, token);
        List<String> resultArguments = new ArrayList<>();
        resultArguments.add(token);
        return new Result(Status.OK, resultArguments);

    }

    private String generateAuthenticationToken() {
        final int size = 24;
        byte[] randomBytes = new byte[size];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private boolean isCorrectPassword(String username, char[] password) throws NoSuchUserException, HashingException {
        User user = userRepository.get(username);
        if (user == null) {
            throw new NoSuchUserException();
        }

        byte[] hashedPassword = user.getHashedPassword();
        byte[] salt = user.getSalt();
        return Password.isMatching(password, salt, hashedPassword);

    }

}
