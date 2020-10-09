package com.splitbills.database.models;

import javax.persistence.*;

@Entity
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String lender;
    private String debtor;
    private double amount;
    private int groupId;

}
