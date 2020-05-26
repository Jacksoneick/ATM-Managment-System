package com.mycompany.app;

import com.mycompany.screens.Login;
import com.mycompany.util.GetPropertyValues;
import com.mycompany.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.io.IOException;

public class Atm {
    GetPropertyValues properties = new GetPropertyValues();
    Integer width;
    Integer height;
    String useSystemTheme;
    JPanel activeScreen;

    {
        try {
            width = Integer.parseInt(properties.getPropValues("width"));
            height = Integer.parseInt(properties.getPropValues("height"));
            useSystemTheme = properties.getPropValues("useSystemTheme");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Atm() {
        //Check config and determine with theme to use.
        if (useSystemTheme.equals("true")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/icon.png"));
        JFrame frame = new JFrame("ATM");
        frame.setIconImage(img.getImage());
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup Hibernate session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Login login = new Login(frame, session);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
