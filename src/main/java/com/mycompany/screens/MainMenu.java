package com.mycompany.screens;

import com.mycompany.storage.User;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {
    private Login parent;
    private JFrame frame;
    private JButton withdrawButton;
    private JButton logOutButton;
    private JButton viewBalance;
    private JButton recentTransactionsButton;
    private Session session;
    private User user;

    public MainMenu(JFrame frame, Login parent, User user, Session session) {
        this.user = user;
        this.frame = frame;
        this.session = session;
        this.parent = parent;
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        Insets inset = new Insets(5, 5, 5, 5);
        constraints.ipadx = 25;
        constraints.ipady = 25;
        constraints.insets = inset;

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 48));
        constraints.gridy = 0;
        this.add(welcomeLabel, constraints);

        withdrawButton = new JButton("Withdraw");
        constraints.gridy = 1;
        this.add(withdrawButton, constraints);
        withdrawButton.addActionListener(this);
        withdrawButton.setActionCommand("Withdraw");

        viewBalance = new JButton("View Balance");
        constraints.gridy = 2;
        this.add(viewBalance, constraints);
        viewBalance.addActionListener(this);
        viewBalance.setActionCommand("ViewBalance");

        recentTransactionsButton = new JButton("View Recent Transactions");
        constraints.gridy = 3;
        this.add(recentTransactionsButton, constraints);
        recentTransactionsButton.addActionListener(this);
        recentTransactionsButton.setActionCommand("RecentTransactions");

        logOutButton = new JButton("Log Out");
        constraints.gridy = 4;
        this.add(logOutButton, constraints);
        logOutButton.addActionListener(this);
        logOutButton.setActionCommand("LogOut");

        frame.add(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Withdraw":
                this.setVisible(false);
                WithdrawScreen withdrawScreen = new WithdrawScreen(frame, session, this, user);
                System.out.println("Withdraw");
                break;
            case "ViewBalance":
                System.out.println("Balance");
                break;
            case "RecentTransactions":
                System.out.println("Recent");
                break;
            case "LogOut":
                this.setVisible(false);
                parent.logOutUser();
                parent.setVisible(true);
                break;
            default:
                break;
        }
    }
}
