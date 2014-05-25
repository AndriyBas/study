package com.fiot.ui;

import com.fiot.app.AppConst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by bamboo on 25.05.14.
 */
public class MainForm extends JFrame {
    private JTabbedPane mTabbedPane1;
    private JPanel rootPane;
    private JComboBox mComboBox2;
    private JComboBox mComboBox3;
    private JComboBox mComboBox4;
    private JComboBox mComboBox5;
    private JButton mПошукButton;
    private JTable mTable1;
    private JTextField mTextFieldFirstName;
    private JTextField mTextFieldSecondName;
    private JTextField mTextFieldEmail;
    private JPasswordField mPasswordField1;
    private JFormattedTextField mFormattedTextFieldPhone;
    private JTextField mTextFieldAddress;
    private JTable mTable2;

    public MainForm() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle"));
        add(rootPane);

        int width = (Integer) AppConst.APP_CONFIG.getValue("mainScreenWidth");
        int height = (Integer) AppConst.APP_CONFIG.getValue("mainScreenHeight");

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        init();
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {

        addJMenu();

    }

    /**
     * ініціалізує меню у програмі
     */
    private void addJMenu() {

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Файл");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        menu.addSeparator();

        menuItem = new JMenuItem("Вихід",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_W, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainForm.this.dispose();
                new LoginForm();
            }
        });
        menu.add(menuItem);


        menu = new JMenu("Інформація");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);


        menuItem = new JMenuItem("Про компанію");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        // TODO
                        "тут інфа про компанію",
                        "Про компанію",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Необхідні документи");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        // TODO
                        "тут інфа",
                        "Необхідні документи",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Вартість послуг");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        // TODO
                        "тут інфа",
                        "Вартість послуг",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Перевезення дітей");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        // TODO
                        "тут інфа",
                        "Перевезення дітей",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);


        menuItem = new JMenuItem("Перевезення тварин");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        // TODO
                        "тут інфа ",
                        "Перевезення тварин",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Перевезення людей із особливими потребами");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        // TODO
                        "тут інфа ",
                        "Перевезення людей із особливими потребами",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);


        menu = new JMenu("Допомога");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Посібник користувача");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        "Допомогло ?",
                        "Посібник користувача",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Про програму");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        "Автор : Іванов Олексій\nВерсія : 1.0",
                        "Про програму",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        setJMenuBar(menuBar);
    }
}
