package com.oyster.ui;

import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bamboo
 * @since 4/13/14 7:18 PM
 */
public class LoginFrame extends JFrame implements ActionListener {

    private JTextField userText;
    private JPasswordField passwordText;

    private String userName = null;
    private String userPassword = null;

    public LoginFrame() {
        super("KPI City");

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setLocationRelativeTo(null);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 170, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 170, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(160, 80, 100, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        StringBuilder errorMsg = new StringBuilder("Введіть ");
        boolean errorOccurred = false;
        JTextComponent focusComponent = userText;

        userName = "admin-root";//userText.getText();
        userPassword = "admin";//new String(passwordText.getPassword());
/*
        if (userName.trim().length() == 0) {
            errorMsg.append(" логін");
            errorOccurred = true;
        }

        if (userPassword.trim().length() == 0) {
            if (errorOccurred) {
                errorMsg.append(" та");
            }
            errorOccurred = true;
            errorMsg.append("  пароль");
            focusComponent = passwordText;
        }*/

        errorMsg.append("!");

        if (!errorOccurred) {
            tryToLogIn();
        } else {
            focusComponent.selectAll();
            JOptionPane.showMessageDialog(
                    LoginFrame.this,
                    errorMsg.toString(),
                    "Спробуйте ще раз",
                    JOptionPane.ERROR_MESSAGE
            );
            userName = null;
            userPassword = null;
            focusComponent.requestFocusInWindow();
        }
    }

    private void tryToLogIn() {

        Context c = new Context();
        c.put("username", userName);
        c.put("password", userPassword);

        try {
            CommandExecutor.getInstance().execute("logIn", c, new Runnable() {
                @Override
                public void run() {
                    LoginFrame.this.dispose();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
