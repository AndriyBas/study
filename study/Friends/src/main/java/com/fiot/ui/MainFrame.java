package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.app.model.User;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by bamboo on 15.05.14.
 */
public class MainFrame extends JFrame {
    private JPanel rootPanel;
    private JList mListFriends;
    private JList mListRequests;
    private JButton mButtonAccept;
    private JButton mButtonRefuse;
    private JList mListAll;
    private JLabel mLabelCurrentUser;

    private ArrayList<User> users;

    /**
     * конструктор, створює елементи інтерфейсу
     */
    public MainFrame() {
        super((String) AppConst.APP_CONFIG.getValue("programTitle"));
        add(rootPanel);
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
        mLabelCurrentUser.setText(AppConst.getCurrentUser().toString() + "("
                + AppConst.getCurrentUser().getEmail() + ")");

        reloadAllUsers();
        reloadFriends();

        mButtonAccept.setEnabled(false);
        mButtonRefuse.setEnabled(false);
    }

    private void reloadAllUsers() {
        String sqlQuery = "select * from USER_TBL where user_id != \""
                + AppConst.getCurrentUser().getId() + "\";";
        reloadUsers(
                sqlQuery,
                mListAll);
    }

    private void reloadFriends() {
        String currentID = AppConst.getCurrentUser().getId().toString();
        String sqlQuery = "select b.* from  FRIEND_RELATION_TBL a " +
                " left join USER_TBL b on ( (a.first_id = b.user_id and a.second_id = \"" + currentID + "\") " +
                " OR ( a.second_id = b.user_id and a.first_id = \"" + currentID + "\" ));";
        reloadUsers(
                sqlQuery,
                mListFriends);
    }

    private void reloadUsers(String sqlQuery, final JList jList) {
        final ArrayList<User> list = new ArrayList<>();
        Context c = new Context();
        c.put("list", list);
        c.put("sqlQuery", sqlQuery);

        try {
            CommandExecutor.getInstance().execute("loadUsers", c, new Runnable() {
                @Override
                public void run() {
                    DefaultListModel<User> model = new DefaultListModel<>();
                    for (User u : list) {
                        model.addElement(u);
                    }
                    jList.setModel(model);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        validate();
        repaint();
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
                MainFrame.this.dispose();
            }
        });
        menu.add(menuItem);

        menu = new JMenu("Допомога");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);

        menuItem = new JMenuItem("Посібник користувача",
                KeyEvent.VK_K);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_K, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Про програму");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        setJMenuBar(menuBar);
    }
}
