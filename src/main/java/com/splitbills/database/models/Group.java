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
    private int id;
    private String name;
    @ManyToMany(mappedBy = "groups", cascade = CascadeType.PERSIST)
    private List<User> users;
    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = Debt.class)
    private List<Debt> debts;
    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = PayRecord.class)
    private List<PayRecord> history;

    public Group() {
        users = new ArrayList<>();
        debts = new ArrayList<>();
    }

    public Group(String name, List<User> users) {
        this.name = name;
        this.users = users;
        debts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.name) &&
                users.equals(group.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, users);
    }
}
