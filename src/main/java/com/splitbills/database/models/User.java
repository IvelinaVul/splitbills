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
    @ManyToMany(cascade = CascadeType.PERSIST) //fix add orphan?
    @JoinTable(name = "User_Group",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "GroupId"))
    private List<BasicGroup> groups;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "User_Friend",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "FriendId"))
    private List<Friend> friends;

}
