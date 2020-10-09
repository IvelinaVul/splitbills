package com.splitbills.database.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    @ManyToMany(mappedBy = "groups")
    private List<User> users;
    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = Debt.class)
    private List<Debt> debts;
    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = PayRecord.class)
    private List<PayRecord> history;

}
