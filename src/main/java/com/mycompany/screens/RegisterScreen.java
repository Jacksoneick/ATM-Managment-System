package com.mycompany.screens;

import com.mycompany.storage.User;
import com.mycompany.util.GetPropertyValues;
import com.mycompany.util.HibernateUtil;
import com.mycompany.util.PasswordHasher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterScreen implements ActionListener, KeyListener {
    Logger logger = Logger.getLogger(RegisterScreen.class.getName());
    GetPropertyValues properties = new GetPropertyValues();
    Integer width;
    Integer height;
    JFrame frame;
    JTextField userFirstLast;
    JButton cancelButton;
    JButton submitButton;
    JPasswordField passwordText;
    JLabel userNameLabel;
    JTextField userNameText;
    JPasswordField confirmPasswordText;
    JLabel confirmPasswordLabel;
    JLabel passwordLabel;

    {
        try {
            width = Integer.parseInt(properties.getPropValues("width"));
            height = Integer.parseInt(properties.getPropValues("height"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RegisterScreen() {
        frame = new JFrame("Register");
        frame.setSize(width, height);
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        Insets inset = new Insets(5, 5, 5, 5);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = inset;
        panel.add(nameLabel, constraints);

        userFirstLast = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(userFirstLast, constraints);

        userNameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = inset;
        panel.add(userNameLabel, constraints);

        userNameText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(userNameText, constraints);

        passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = inset;
        panel.add(passwordLabel, constraints);

        passwordText = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordText, constraints);

        confirmPasswordLabel = new JLabel("Confirm Password:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = inset;
        panel.add(confirmPasswordLabel, constraints);

        confirmPasswordText = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(confirmPasswordText, constraints);
        confirmPasswordText.addKeyListener(this);

        cancelButton = new JButton("Cancel");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(cancelButton, constraints);
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand("Cancel");

        submitButton = new JButton("Submit");
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(submitButton, constraints);
        submitButton.addActionListener(this);
        submitButton.setActionCommand("Submit");


        frame.add(panel);

        frame.setVisible(true);
    }


    public Boolean registerUser() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            //Very non accessible name parsing, should definitely update this.
            String[] name = userFirstLast.getText().split(" ");
            String firstName = name[0];
            String lastName = name[1];
            byte[] salt = PasswordHasher.getSalt();
            String password = PasswordHasher.get_SHA_1_SecurePassword(new String(passwordText.getPassword()), salt);
            User user = new User(null, userNameText.getText(), firstName, lastName,
                    password, salt, 0.00);
            session.save(user);
            session.getTransaction().commit();
            logger.log(Level.INFO, "User Created");
        } catch (Throwable throwable) {
            logger.log(Level.WARNING, "Failed to create user: " + throwable);
            session.getTransaction().rollback();
            return false;
        }

        return true;

    }

    private boolean passwordsMatch(char[] password, char[] confirmPassword) {
        if (!new String(passwordText.getPassword()).equals(new String(confirmPasswordText.getPassword()))) {
            return true;
        }
        return false;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (passwordsMatch(passwordText.getPassword(), confirmPasswordText.getPassword())) {
            confirmPasswordText.setBackground(Color.RED);
        } else {
            confirmPasswordText.setBackground(Color.GREEN);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Cancel":
                frame.setVisible(false);
                break;
            case "Submit":
                Boolean success = registerUser();
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Thank you for registering!");
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "An error occurred, please try again.");
                }
                break;
            default:
                break;
        }
    }
}
