package com.fiot.ui;

import com.fiot.app.AppConst;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bamboo on 25.05.14.
 */
public class RegisterForm extends JFrame {
    private JTextField mTextFieldFirstName;
    private JTextField mTextFieldSecondName;
    private JTextField mTextFieldEmail;
    private JPasswordField mPasswordField1;
    private JFormattedTextField mFormattedTextFieldPhone;
    private JTextField mTextFieldAddress;
    private JButton mРеєстраціяButton;
    private JPanel rootPane;

    public RegisterForm() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle"));
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

    private void init() {
    }
}
