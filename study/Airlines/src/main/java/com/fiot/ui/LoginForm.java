package com.fiot.ui;

import com.fiot.app.AppConst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bamboo on 25.05.14.
 */
public class LoginForm extends JFrame {

    private JTextField mFieldEmail;
    private JPasswordField mFieldPassword;
    private JButton mButtonLogIn;
    private JButton mButtonRegister;
    private JPanel rootPane;

    public LoginForm() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle"));
        add(rootPane);

        int width = (Integer) AppConst.APP_CONFIG.getValue("logInScreenWidth");
        int height = (Integer) AppConst.APP_CONFIG.getValue("logInScreenHeight");

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        init();
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        mButtonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterForm();
                LoginForm.this.dispose();
            }
        });

        mButtonLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryToLogIn();
            }
        });
    }

    private void tryToLogIn() {
        new MainForm();
        this.dispose();
    }

}
