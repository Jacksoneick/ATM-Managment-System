package com.mycompany.app;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "transactionID", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "balance")
    private Float balance;

    public User(Integer userId, String firstName, String lastName, Float balance) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Float getBalance() {
        return balance;
    }
}
