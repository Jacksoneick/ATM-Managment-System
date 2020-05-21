package com.mycompany.app;

import com.mycompany.util.GetPropertyValues;
import com.mycompany.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterScreen implements ActionListener {
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
    JTextField userText;
    JTextField userNameText;

    {
        try {
            width = Integer.parseInt(properties.getPropValues("width"));
            height = Integer.parseInt(properties.getPropValues("height"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init() {
        RegisterScreen registerScreen = new RegisterScreen();
        frame = new JFrame("Register");
        frame.setSize(width, height);
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

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = inset;
        panel.add(passwordLabel, constraints);

        passwordText = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordText, constraints);

        cancelButton = new JButton("Cancel");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(cancelButton, constraints);
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand("Cancel");


        submitButton = new JButton("Submit");
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(submitButton, constraints);
        submitButton.addActionListener(this);
        submitButton.setActionCommand("Submit");


        frame.add(panel);

        frame.setVisible(true);
    }

    public Boolean registerUser(char[] password) {
        try {
            //Very non accessible name parsing, should definitely update this.
            String[] name = userFirstLast.getText().split(" ");
            System.out.println(Arrays.toString(name));
            String firstName = name[0];
            String lastName = name[1];
            User user = new User(null, userNameText.getText(), firstName, lastName, 0.00);
            System.out.println("User Registered, " + "Password:" + new String(password));
            SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            logger.log(Level.INFO, "User Created");
            sessionFactory.close();
        } catch (Throwable throwable) {
            logger.log(Level.WARNING, "Failed to create user: " + throwable);
            return false;
        }

        return true;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Cancel":
                frame.setVisible(false);
                break;
            case "Submit":
                Boolean success = registerUser(passwordText.getPassword());
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
