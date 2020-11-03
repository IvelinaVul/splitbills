package com.splitbills.database;

import com.splitbills.database.models.Debt;
import com.splitbills.database.models.Group;
import com.splitbills.database.models.PayRecord;
import com.splitbills.database.models.User;

import java.util.List;

public interface GroupRepository {

    Long addGroup(Group group);

    void addPayRecord(String username, String groupname, PayRecord payRecord) throws NoSuchGroupException;

    List<Group> getGroups(String username);

    List<Debt> getDebts(String username, String groupname) throws NoSuchGroupException;

    void updateDebt(Debt debt);

    List<User> getUsers(String username, String groupname) throws NoSuchGroupException;

}
