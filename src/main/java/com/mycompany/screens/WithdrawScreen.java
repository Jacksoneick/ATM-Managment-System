package com.mycompany.screens;

import com.mycompany.storage.User;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WithdrawScreen extends JPanel implements ActionListener {
    JButton withdraw20;
    JButton withdraw50;
    JButton withdraw100;
    JTextField withdrawCustomText;
    JButton withdrawCustomButton;
    Session session;
    User user;
    Logger logger;

    public WithdrawScreen(JFrame frame, Session session, JPanel parent, User user) {
        this.logger = Logger.getLogger(WithdrawScreen.class.getName());
        this.user = user;
        this.session = session;
        this.setLayout(new GridBagLayout());
        Insets inset = new Insets(5, 5, 5, 5);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = 25;
        constraints.ipady = 25;
        constraints.insets = inset;

        JLabel header = new JLabel("Withdraw", JLabel.CENTER);
        header.setFont(new Font("Serif", Font.BOLD, 48));
        constraints.gridy = 0;
        this.add(header, constraints);

        withdraw20 = new JButton("$20");
        withdraw20.setFont(new Font("Serif", Font.BOLD, 12));
        constraints.gridy = 1;
        this.add(withdraw20, constraints);
        withdraw20.addActionListener(this);
        withdraw20.setActionCommand("Withdraw20");

        withdraw50 = new JButton("$50");
        withdraw50.setFont(new Font("Serif", Font.BOLD, 12));
        constraints.gridy = 2;
        this.add(withdraw50, constraints);
        withdraw50.addActionListener(this);
        withdraw50.setActionCommand("Withdraw50");

        withdraw100 = new JButton("$100");
        withdraw100.setFont(new Font("Serif", Font.BOLD, 12));
        constraints.gridy = 3;
        this.add(withdraw100, constraints);
        withdraw100.addActionListener(this);
        withdraw100.setActionCommand("Withdraw100");

        JLabel customAmountLabel = new JLabel("Withdraw a custom amount:");
        customAmountLabel.setFont(new Font("Serif", Font.BOLD, 18));
        constraints.gridy = 4;
        this.add(customAmountLabel, constraints);

        withdrawCustomText = new JTextField("$0.00", 36);
        withdrawCustomText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCustomButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCustomButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCustomButton();
            }
        });
        constraints.gridy = 5;
        PlainDocument withdrawCustomDocument = (PlainDocument) withdrawCustomText.getDocument();
        withdrawCustomDocument.setDocumentFilter(new InputFilter());
        this.add(withdrawCustomText, constraints);

        withdrawCustomButton = new JButton("Withdraw $0.00");
        constraints.gridy = 6;
        this.add(withdrawCustomButton, constraints);
        withdrawCustomButton.addActionListener(this);
        withdrawCustomButton.setActionCommand("WithdrawCustom");

        frame.add(this);
    }

    private void updateCustomButton() {
        withdrawCustomButton.setText("Withdraw $" + withdrawCustomText.getText());
        withdrawCustomButton.updateUI();
    }

    private void withdraw(Double amount) {
        logger.log(Level.INFO, "User " + user.getUserName() + " withdrew: " + amount);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Withdraw20":
                withdraw(20.00);
                break;
            case "Withdraw50":
                withdraw(50.00);
                break;
            case "Withdraw100":
                withdraw(100.00);
                break;
            case "WithdrawCustom":
                String customAmount = withdrawCustomText.getText().replace("$", "");
                withdraw(Double.parseDouble(customAmount));
                break;
        }
    }

    class InputFilter extends DocumentFilter {
        // Derived from https://stackoverflow.com/a/11093360.
        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (testInput(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // warn the user and don't allow the insert
            }
        }


        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (testInput(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // warn the user and don't allow the insert
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (testInput(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                // warn the user and don't allow the insert
            }

        }

        private boolean testInput(String text) {
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}

