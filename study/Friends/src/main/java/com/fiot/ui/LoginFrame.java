package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Клас відповідає за вікно, що здійснює авторизацію користувача
 */
public class LoginFrame extends JFrame implements ActionListener {

    private JTextField userText;
    private JPasswordField passwordText;

    private String userName = null;
    private String userPassword = null;

    /**
     * Конструктор форми
     */
    public LoginFrame() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle"));
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setLocationRelativeTo(null);
    }

    /**
     * ініціалізує компоненти
     *
     * @param panel панель, на якуі помістити компоненти
     */
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
        loginButton.setBounds(10, 80, 260, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);
    }

    /**
     * спрацьовує на подію e
     *
     * @param e подія
     */
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

    /**
     * викликає команду здійснення авторизації
     */
    private void tryToLogIn() {

        new MainFrame();
      /*  Context c = new Context();
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
        }*/

    }
}
