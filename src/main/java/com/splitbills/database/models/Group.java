package com.splitbills.database.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @ManyToMany(mappedBy = "group")
    private List<User> users;
    @OneToMany
    private List<Debt> debts;
    @OneToMany
    private List<Record> history;
}
