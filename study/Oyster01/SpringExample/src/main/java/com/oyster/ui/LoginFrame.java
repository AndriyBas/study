package com.oyster.ui;

import com.oyster.app.AppConst;
import com.oyster.app.model.Admin;
import com.oyster.app.model.Profile;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.dao.impl.DAOCRUDJdbc;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

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

//        frame.pack();
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

        DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(AppConst.context);

        Timestamp t = new Timestamp(100L);

        List<Profile> profiles = null;
        try {
            profiles = x.select(Profile.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Profile p = (Profile) entity;
                    return userName.equals(p.getName() + "-" + p.getSurname())
                            && userPassword.equals(p.getPassword());
                }
            });
        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(this, Utils.makePretty(e.getMessage()));
            return;
        }

        if (profiles.size() == 0) {
            Utils.showErrorDialog(this, "Помилка авторизації : неправильні логін чи пароль !");
            return;
        }

        final Profile p = profiles.get(0);
        List<Admin> admins = null;

        try {
            admins = x.select(Admin.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Admin a = (Admin) entity;
                    return a.getProfileId().equals(p.getId());
                }
            });
        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(this, Utils.makePretty(e.getMessage()));
            return;
        }

        if (admins.size() == 0) {
            Utils.showErrorDialog(this, "Помилка авторизації : дані логін та пароль не відповідають адміністратору !");
            return;
        }

        AppConst.setCurrentAdmin(admins.get(0));
        AppConst.setCurrentAdminProfile(p);

        MainForm form = new MainForm();

        this.dispose();

    }
}
