package com.splitbills.database.models;

import javax.persistence.*;

@Entity
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String lender;
    private String debtor;
    private double amount;
    private Long groupId;

}
