package com.splitbills.database.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToMany(targetEntity = User.class,cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private List<User> users;

    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = Debt.class)
    private List<Debt> debts;
    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = PayRecord.class)
    private List<PayRecord> history;

    public Group() {

    }

    public Group(String name, List<User> users) {
        this.name = name;
        this.users = users;
        debts = new ArrayList<>();
        history = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }
}
