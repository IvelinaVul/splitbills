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
    @ManyToMany(cascade = {CascadeType.PERSIST}, targetEntity = Group.class)
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
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

    public List<Group> getGroups() {
        return groups;
    }
}
