package com.mycompany.app;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionID", nullable = false, unique = true)
    int transactionId;

    @Column(name = "userId")
    int userId;

    @Column(name = "location")
    String location;

    @Column(name = "amount")
    Double amount;

    public Transaction(int transactionId, int userId, String location, Double amount) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.location = location;
        this.amount = amount;
    }
}
