package com.splitbills.database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    @ManyToMany(mappedBy = "users", targetEntity = Group.class)

    private List<Group> groups;

    public User() {

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
}
