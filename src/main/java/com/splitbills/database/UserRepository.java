package com.splitbills.database;

import com.splitbills.database.models.User;

public interface UserRepository {
    public void add(User user) throws UserAlreadyExistsException;
    public User get(String username);
    public boolean contains(User user);
}
