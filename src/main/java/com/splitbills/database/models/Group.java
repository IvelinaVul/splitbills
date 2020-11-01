package com.splitbills.database.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private List<User> users;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Debt.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private List<Debt> debts;
    @OneToMany(mappedBy = "groupId", cascade = CascadeType.ALL, targetEntity = PayRecord.class, fetch = FetchType.LAZY)
    private List<PayRecord> history;

    public Group() {
        users = new ArrayList<>();
        debts = new ArrayList<>();
        history = new ArrayList<>();
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

    public List<Debt> getDebts() {
        return debts;
    }

    public void addDebt(Debt debt) {
        debts.add(debt);
    }

    public List<PayRecord> getHistory() {
        return history;
    }

    public void addPayRecord(PayRecord payRecord) {
        history.add(payRecord);
    }

    public List<User> getUsers() {
        return users;
    }
}
