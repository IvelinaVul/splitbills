package com.splitbills.database;

import com.splitbills.database.models.User;

public interface UserRepository {

    String add(User user) throws UserAlreadyExistsException;

    User get(String username) throws NoSuchUserException;

    boolean contains(String username);
}
