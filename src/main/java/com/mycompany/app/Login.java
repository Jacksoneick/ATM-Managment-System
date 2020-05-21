package com.mycompany.app;

import com.mycompany.util.GetPropertyValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login implements ActionListener {
    GetPropertyValues properties = new GetPropertyValues();

    JPanel panel;
    Integer width;
    Integer height;

    {
        try {
            width = Integer.parseInt(properties.getPropValues("width"));
            height = Integer.parseInt(properties.getPropValues("height"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init(JPanel panel, GridBagConstraints constraints) {
        this.panel = panel;
        LoginScreen(panel, constraints);

    }

    private void LoginScreen(JPanel panel, GridBagConstraints constraints) {
        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        panel.setLayout(new GridBagLayout());
        Insets inset = new Insets(5, 5, 5, 5);

        // Creating JLabel
        JLabel userLabel = new JLabel("User");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(userLabel, constraints);

        JTextField userText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(userText, constraints);

        // Same process for password label and text field.
        JLabel passwordLabel = new JLabel("Password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipadx = 5;
        panel.add(passwordLabel, constraints);

        JPasswordField passwordText = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = inset;
        panel.add(passwordText, constraints);

        JButton loginButton = new JButton("Login");
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(loginButton, constraints);

        JButton registerButton = new JButton("Register");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = inset;
        panel.add(registerButton, constraints);
        registerButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RegisterScreen registerScreen = new RegisterScreen();
        registerScreen.init();


    }
}
