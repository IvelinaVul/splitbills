package com.splitbills.database;

import com.splitbills.database.models.Group;

import java.util.List;

public interface GroupRepository {

    Long add(Group group);

    List<Group> getAll(String username);
}
