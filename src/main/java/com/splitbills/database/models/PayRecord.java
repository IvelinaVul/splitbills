package com.splitbills.database.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PayRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String lender;
    private String debtor;
    private double amount;
    private String reason;
    private Long groupId;

    public PayRecord() {

    }

    public PayRecord(String lender, String debtor, double amount, String reason) {
        this.lender = lender;
        this.debtor = debtor;
        this.amount = amount;
        this.reason = reason;
    }
}
