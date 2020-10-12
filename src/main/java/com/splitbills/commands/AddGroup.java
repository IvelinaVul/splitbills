package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.Group;
import com.splitbills.database.models.User;

import java.util.ArrayList;
import java.util.List;

public class AddGroup extends Command {

    public AddGroup(UserRepository userRepository, GroupRepository groupRepository) {
        super(userRepository, groupRepository);
    }

    @Override
    public Result execute(List<String> arguments, String token) {
        if (!isValid(arguments)) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        String groupName = arguments.get(0);
        if (groupName == null) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        List<User> users;
        try {
            users = getUsers(arguments);
        } catch (NoSuchUserException noSuchUserException) {
            return new Result(Status.NOT_REGISTERED);
        }
        Group group = new Group(groupName, users);
        groupRepository.add(group);
        return new Result(Status.OK);
    }

    private boolean isValid(List<String> arguments) {
        int expectedAtLeast = 3;

        return arguments != null && arguments.size() >= expectedAtLeast;
    }

    private List<User> getUsers(List<String> usernames) throws NoSuchUserException {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < usernames.size(); ++i) {
            User current = userRepository.get(usernames.get(i));
            if (current != null) {
                throw new NoSuchUserException();
            }
            users.add(current);
        }
        return users;
    }
}