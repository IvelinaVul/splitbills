package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.NoSuchGroupException;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.Debt;
import com.splitbills.database.models.PayRecord;
import com.splitbills.database.models.User;

import java.util.List;
import java.util.Map;

public class SplitGroup extends Command {

    private final static int USERNAME = 0;
    private final static int GROUPNAME = 1;
    private final static int AMOUNT = 2;
    private final static int REASON = 3;

    SplitGroup(UserRepository userRepository, GroupRepository groupRepository, Map<String, String> loggedInUsers) {
        super(userRepository, groupRepository, loggedInUsers);
    }

    @Override
    public Result execute(List<String> arguments, String token) {
        if (!isValid(arguments)) {
            return new Result(Status.INVALID_ARGUMENTS);
        }
        String username = arguments.get(USERNAME);
        String groupName = arguments.get(GROUPNAME);
        String amountToBeSplit = arguments.get(AMOUNT);
        String reason = arguments.get(REASON);
        if (!isLoggedInWith(username, token)) {
            return new Result(Status.NOT_LOGGED_IN);
        }
        double fullAmount = Double.parseDouble(amountToBeSplit);
        try {
            if (!isPartOfTheGroup(username, groupName)) {
                return new Result(Status.INVALID_ARGUMENTS);
            }
            split(username, groupName, fullAmount, reason);
        } catch (NoSuchGroupException noSuchGroupException) {
            return new Result(Status.NOT_EXISTING);
        }
        return new Result(Status.OK);
    }

    private boolean isValid(List<String> arguments) {
        int expectedAtLeast = 3;
        if (arguments != null && arguments.size() >= expectedAtLeast) {
            String username = arguments.get(USERNAME);
            String groupName = arguments.get(GROUPNAME);
            String amount = arguments.get(AMOUNT);
            return username != null && groupName != null && amount != null;
        }
        return false;
    }

    private boolean isLoggedInWith(String groupCreator, String token) {
        return loggedInUsers.containsKey(groupCreator) && loggedInUsers.get(groupCreator).equals(token);
    }

    private boolean isPartOfTheGroup(String username, String groupname) throws NoSuchGroupException {
        List<User> members = groupRepository.getUsers(username, groupname);
        return members.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    private void split(String lender, String groupname, double fullAmount, String reason) throws NoSuchGroupException {
        int members = groupRepository.getUsers(lender, groupname).size();
        double amountPerPerson = fullAmount / members;
        List<Debt> debts = groupRepository.getDebts(lender, groupname);
        for (Debt debt : debts) {
            if (lender.equalsIgnoreCase(debt.getLender())) {
                PayRecord payRecord = new PayRecord(debt.getLender(), debt.getDebtor(), amountPerPerson, reason);
                groupRepository.addPayRecord(lender, groupname, payRecord);
                splitWhenLender(debt, amountPerPerson);
            } else if (lender.equalsIgnoreCase(debt.getDebtor())) {
                PayRecord payRecord = new PayRecord(debt.getLender(), debt.getDebtor(), amountPerPerson, reason);
                groupRepository.addPayRecord(lender, groupname, payRecord);
                splitWhenDebtor(debt, amountPerPerson);
            }
        }

    }

    private void splitWhenLender(Debt debt, double amountPerPerson) {
        double currentAmount = debt.getAmount();
        debt.setAmount(currentAmount + amountPerPerson);
        groupRepository.updateDebt(debt);
    }

    private void splitWhenDebtor(Debt debt, double amountPerPerson) {
        double currentAmount = debt.getAmount();
        currentAmount = currentAmount - amountPerPerson;
        debt.setAmount(currentAmount);
        if (currentAmount < 0) {
            debt.setAmount(-currentAmount);
            String previousLender = debt.getLender();
            String previousDebtor = debt.getDebtor();
            debt.setLender(previousDebtor);
            debt.setDebtor(previousLender);
        }
        groupRepository.updateDebt(debt);
    }

}
