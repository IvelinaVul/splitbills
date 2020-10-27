package com.splitbills.database.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    private String username;
    @Column(nullable = false)
    private byte[] hashedPassword;
    @Column(nullable = false)
    private byte[] salt;
    @ManyToMany(mappedBy = "users", targetEntity = Group.class, fetch = FetchType.LAZY)

    private List<Group> groups;

    public User() {
        groups = new ArrayList<>();
    }

    public User(String username, byte[] hashedPassword, byte[] salt) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        groups = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public List<Group> getGroups() {
        return groups;
    }

}
