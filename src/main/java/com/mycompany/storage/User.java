package com.mycompany.storage;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", nullable = false, unique = true)
    Integer userId;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "balance")
    Double balance;

    @Column(name = "password")
    String password;

    @Column(name = "userName")
    String userName;

    @Column(name = "passwordSalt")
    byte[] passwordSalt;


    public User(Integer userId, String userName, String firstName, String lastName, String password, byte[] passwordSalt, Double balance) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.balance = balance;
        this.passwordSalt = passwordSalt;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
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

    public Double getBalance() {
        return balance;
    }
}
