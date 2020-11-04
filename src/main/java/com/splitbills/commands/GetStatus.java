package com.splitbills.commands;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.splitbills.database.GroupRepository;
import com.splitbills.database.NoSuchGroupException;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.Debt;
import com.splitbills.database.models.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetStatus extends Command {

    private final static int USERNAME = 0;
    private Gson gson;

    GetStatus(UserRepository userRepository, GroupRepository groupRepository, Map<String, String> loggedInUsers) {
        super(userRepository, groupRepository, loggedInUsers);
        ExclusionStrategy idExclusionStrategy = new IdExclusionStrategy();
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(idExclusionStrategy)
                .create();

    }

    @Override
    public Result execute(List<String> arguments, String token) {
        if (arguments == null || token == null) {
            return new Result(Status.INVALID_ARGUMENTS);

        }
        String username = arguments.get(USERNAME);
        if (username == null) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        List<Group> groups = groupRepository.getGroups(username);
        try {
            List<Debt> debts = getDebts(username, groups);
            String json = gson.toJson(debts);
            List<String> output = new ArrayList<>();
            output.add(json);
            return new Result(Status.OK, output);
        } catch (NoSuchGroupException noSuchGroupException) {
            return new Result(Status.NOT_EXISTING);
        }

    }

    private List<Debt> getDebts(String username, List<Group> groups) throws NoSuchGroupException {
        List<Debt> allDebts = new ArrayList<>();
        for (Group group : groups) {
            List<Debt> currentGroupDebts = groupRepository.getDebts(username, group.getName());
            List<Debt> notZeroDebts = currentGroupDebts.stream()
                    .filter(debt -> debt.getAmount() > 0)
                    .collect(Collectors.toList());
            allDebts.addAll(notZeroDebts);
        }
        return allDebts;
    }
}
