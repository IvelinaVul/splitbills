package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.Debt;
import com.splitbills.database.models.Group;
import com.splitbills.database.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddGroup extends Command {

    AddGroup(UserRepository userRepository, GroupRepository groupRepository, Map<String, String> loggedInUsers) {
        super(userRepository, groupRepository, loggedInUsers);
    }

    @Override
    public Result execute(List<String> arguments, String token) {
        if (!isValid(arguments)) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        String groupName = arguments.get(0);
        String groupCreator = arguments.get(1);
        Result result;

        if (isLoggedInWith(groupCreator, token)) {
            arguments.remove(0);
            try {
                addGroup(groupName, arguments);
                result = new Result(Status.OK);
            } catch (NoSuchUserException noSuchUserException) {
                result = new Result(Status.NOT_LOGGED_IN);
            }
        } else {
            result = new Result(Status.NOT_LOGGED_IN);
        }
        return result;

    }

    private boolean isValid(List<String> arguments) {
        int expectedAtLeast = 3;
        if (arguments != null && arguments.size() >= expectedAtLeast) {
            String groupName = arguments.get(0);
            String groupCreator = arguments.get(1);
            return groupName != null && groupCreator != null;
        }
        return false;
    }

    private boolean isLoggedInWith(String groupCreator, String token) {
        return loggedInUsers.containsKey(groupCreator) && loggedInUsers.get(groupCreator).equals(token);
    }

    private void addGroup(String groupName, List<String> usernames) throws NoSuchUserException {
        List<User> groupUsers;
        groupUsers = getUsers(usernames);

        Group group = new Group(groupName, groupUsers);

        List<Debt> debts = getDebts(usernames);
        for (Debt debt : debts) {
            group.addDebt(debt);
        }
        groupRepository.addGroup(group);

    }

    private List<User> getUsers(List<String> usernames) throws NoSuchUserException {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < usernames.size(); ++i) {
            User current = userRepository.get(usernames.get(i));
            if (current == null) {
                throw new NoSuchUserException();
            }
            users.add(current);
        }
        return users;
    }

    private List<Debt> getDebts(List<String> usernames) {
        List<Debt> debts = new ArrayList<>();
        double amount = 0;
        for (int i = 0; i < usernames.size() - 1; ++i) {
            for (int j = i + 1; j < usernames.size(); ++j) {
                String lender = usernames.get(i);
                String debtor = usernames.get(j);
                Debt newDebt = new Debt(lender, debtor, amount);
                debts.add(newDebt);
            }
        }
        return debts;
    }

}