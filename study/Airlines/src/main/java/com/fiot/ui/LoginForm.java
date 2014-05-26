package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Клас Інтерфейсу, відповідає за вікно авторизації
 */
public class LoginForm extends JFrame {

    private JTextField mFieldEmail;
    private JPasswordField mFieldPassword;
    private JButton mButtonLogIn;
    private JButton mButtonRegister;
    private JPanel rootPane;


    private String userName;
    private String userPassword;

    /**
     * Конструктор, створює компоненти
     */
    public LoginForm() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle") + " : Авторизація");
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

    /**
     * ініціалізує компоненти
     */
    private void init() {

        //****************************************
        mFieldEmail.setText("losha@gmail.com");
        mFieldPassword.setText("password");
        //*******************************************


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
                StringBuilder errorMsg = new StringBuilder("Введіть ");
                boolean errorOccurred = false;
                JTextComponent focusComponent = mFieldEmail;

                userName = mFieldEmail.getText();
                userPassword = new String(mFieldPassword.getPassword());

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
                    focusComponent = mFieldPassword;
                }

                errorMsg.append("!");

                if (!errorOccurred) {
                    tryToLogIn();
                } else {
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            LoginForm.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    userName = null;
                    userPassword = null;
                    focusComponent.requestFocusInWindow();
                }
            }
        });
    }

    /**
     * відправляє команду реєстрації у контролер
     */
    private void tryToLogIn() {

        Context c = new Context();
        c.put("username", userName);
        c.put("password", userPassword);

        try {
            CommandExecutor.getInstance().execute("logIn", c, new Runnable() {
                @Override
                public void run() {
                    LoginForm.this.dispose();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
