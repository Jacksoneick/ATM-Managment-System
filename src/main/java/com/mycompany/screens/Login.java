package com.mycompany.screens;

import com.mycompany.storage.User;
import com.mycompany.util.GetPropertyValues;
import com.mycompany.util.HibernateUtil;
import com.mycompany.util.PasswordHasher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JPanel implements ActionListener {
    Logger logger = Logger.getLogger(RegisterScreen.class.getName());
    GetPropertyValues properties = new GetPropertyValues();
    //JPanel loginPanel;
    Integer width;
    Integer height;
    JFrame frame;
    JTextField userText;
    JPasswordField passwordText;
    JButton loginButton;
    User user;
    Session session;

    {
        try {
            width = Integer.parseInt(properties.getPropValues("width"));
            height = Integer.parseInt(properties.getPropValues("height"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Login (JFrame frame, Session session)  {
        this.session = session;
        this.frame = frame;
        this.frame.getRootPane().setDefaultButton(loginButton);
        //this.loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        this.frame.add(this);
        Insets inset = new Insets(5, 5, 5, 5);


        JLabel userLabel = new JLabel("User");
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(userLabel, constraints);

        userText = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(userText, constraints);

        JLabel passwordLabel = new JLabel("Password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipadx = 5;
        this.add(passwordLabel, constraints);

        passwordText = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = inset;
        this.add(passwordText, constraints);

        loginButton = new JButton("Login");
        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(loginButton, constraints);
        loginButton.addActionListener(this);
        loginButton.setActionCommand("Login");

        JButton registerButton = new JButton("Register");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = inset;
        this.add(registerButton, constraints);
        registerButton.addActionListener(this);
        registerButton.setActionCommand("Register");

    }

    public void logOutUser() {
        this.user = null;
    }

    private boolean setUser() {
        try {
            // Get the session if it is still open, and create a new one if it is not.
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String username = userText.getText();
            Query query = session.createQuery("select user from User user where user.userName = :username");
            query.setParameter("username", username);

            User user = (User) query.list().get(0);
            session.getTransaction().commit();

            this.user = user;
            return true;

        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(frame, "Username/Password was incorrect.\n Please try again.");
            session.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Fetching user failed: " + e.toString());
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean loginUser() {
        if (setUser()) {
            try {
                byte[] salt = user.getPasswordSalt();
                String hashedPassword = PasswordHasher.get_SHA_1_SecurePassword(new String(passwordText.getPassword()), salt);
                if (hashedPassword.equals(user.getPassword())) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Username/Password was incorrect.\n Please try again.");
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(frame, "Username/Password was incorrect.\n Please try again.");
                return false;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "An error occurred, please try again.");
                logger.log(Level.WARNING, "Creating a user failed because : " + e.toString());
                return false;
            }
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Register":
                RegisterScreen registerScreen = new RegisterScreen();
                break;
            case "Login":
                if (loginUser()) {
                    MainMenu mainMenu = new MainMenu(frame, this, user, session);
                    this.setVisible(false);
                }
                break;
            default:
                break;
        }


    }
}
