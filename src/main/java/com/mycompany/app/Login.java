package com.mycompany.app;

import com.mycompany.util.GetPropertyValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login implements ActionListener {
    GetPropertyValues properties = new GetPropertyValues();

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
        LoginScreen(panel, constraints);

//        Transaction transaction = new Transaction(1, 1, "Paris", 500.00);
//        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        session.save(transaction);
//        session.getTransaction().commit();
//        sessionFactory.close();
    }

    private void LoginScreen(JPanel panel, GridBagConstraints constraints) {
        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        panel.setLayout(new GridBagLayout());

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
        panel.add(passwordText, constraints);

        JButton loginButton = new JButton("login");
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(loginButton, constraints);

        JButton registerButton = new JButton("register");
        Insets inset = new Insets(5, 5, 5, 5);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = inset;
        panel.add(registerButton, constraints);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
