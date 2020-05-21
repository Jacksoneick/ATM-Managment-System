package com.mycompany.app;

import com.mycompany.util.GetPropertyValues;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Atm {
    GetPropertyValues properties = new GetPropertyValues();
    Integer width;
    Integer height;
    String useSystemTheme;

    {
        try {
            width = Integer.parseInt(properties.getPropValues("width"));
            height = Integer.parseInt(properties.getPropValues("height"));
            useSystemTheme = properties.getPropValues("useSystemTheme");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        //Check config and determine with theme to use.
        if (useSystemTheme == "true") {
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


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        frame.add(panel);

        Login login = new Login();
        login.init(panel, constraints);
        frame.setVisible(true);

    }
}
