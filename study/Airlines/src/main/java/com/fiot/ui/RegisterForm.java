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
 * Клас інтерфейсу, відповідає за вікно реєстрації
 */
public class RegisterForm extends JFrame {
    private JTextField mTextFieldFirstName;
    private JTextField mTextFieldSecondName;
    private JTextField mTextFieldEmail;
    private JPasswordField mPasswordField1;
    private JFormattedTextField mFormattedTextFieldPhone;
    private JTextField mTextFieldAddress;
    private JButton mButtonRegister;
    private JPanel rootPane;

    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String phone;
    private String address;

    /**
     * конструктор, визначає розміри та наповнення вікна
     */
    public RegisterForm() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle") + " : Реєстрація");
        add(rootPane);

        int width = (Integer) AppConst.APP_CONFIG.getValue("registerScreenWidth");
        int height = (Integer) AppConst.APP_CONFIG.getValue("registerScreenHeight");

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

        mButtonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                firstName = mTextFieldFirstName.getText().trim();
                secondName = mTextFieldSecondName.getText().trim();
                email = mTextFieldEmail.getText().trim();
                password = mPasswordField1.getText().trim();
                phone = mFormattedTextFieldPhone.getText().trim();
                address = mTextFieldAddress.getText().trim();

                boolean errorOccured = false;
                JTextComponent focusComponent = mTextFieldFirstName;


                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (firstName.length() == 0) {
                    errorMsg.append(" ім’я ");
                    errorOccured = true;
                }

                if (secondName.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  прізвище");
                    focusComponent = mTextFieldSecondName;
                }
                if (email.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append(" email");
                    focusComponent = mTextFieldEmail;
                }

                if (password.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  пароль");
                    focusComponent = mPasswordField1;
                }

                if (phone.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  телефон");
                    focusComponent = mFormattedTextFieldPhone;
                }
                if (address.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  адресу");
                    focusComponent = mTextFieldAddress;
                }

                errorMsg.append("!");

                if (!errorOccured) {
                    tryToRegister();
                } else {
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            RegisterForm.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    firstName = null;
                    secondName = null;
                    email = null;
                    password = null;
                    phone = null;
                    address = null;
                    focusComponent.requestFocusInWindow();
                }
            }
        });
    }

    /**
     * збирає дані та викликає команду реєстрації
     */
    private void tryToRegister() {

        // collect all data
        Context c = new Context();
        c.put("first_name", firstName);
        c.put("second_name", secondName);
        c.put("email", email);
        c.put("password", password);
        c.put("phone", phone);
        c.put("address", address);

        try {
            CommandExecutor.getInstance().execute("registerUser", c, new Runnable() {
                @Override
                public void run() {
                    int result = JOptionPane.showConfirmDialog(
                            RegisterForm.this,
                            secondName + " " + firstName + ", Ви зареєстровані успішно !\nАвторизуватись ?",
                            "Реєстрація успішна",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (result == JOptionPane.YES_OPTION) {

                        Context c2 = new Context();
                        c2.put("username", email);
                        c2.put("password", password);

                        try {
                            CommandExecutor.getInstance().execute("logIn", c2, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        new LoginForm();
                    }
                    RegisterForm.this.dispose();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
