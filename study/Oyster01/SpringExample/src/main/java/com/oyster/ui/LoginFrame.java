package com.oyster.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bamboo
 * @since 4/13/14 7:18 PM
 */
public class LoginFrame extends JFrame implements ActionListener {

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

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 170, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 170, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(160, 80, 100, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MainForm form = new MainForm();
        /*
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        mainFrame.setVisible(true);

        MainForm f = new MainForm();
*/
        this.dispose();
    }
}
