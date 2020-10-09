package com.splitbills.database.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    private String username;
    @Column(nullable = false)
    private char[] hashedPassword;
    @Column(nullable = false)
    private byte[] salt;
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Group.class)
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;
}
