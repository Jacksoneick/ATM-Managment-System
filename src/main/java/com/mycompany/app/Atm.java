package com.mycompany.app;

import com.mycompany.util.GetPropertyValues;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Atm {
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

    public void init() {
        JFrame frame = new JFrame("ATM");
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
