package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.app.model.User;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by bamboo on 15.05.14.
 */
public class MainFrame extends JFrame {
    private JPanel rootPanel;

    private JList mListFriends;
    private JList mListRequests;
    private JList mListAll;

    private JButton mButtonAccept;
    private JButton mButtonRefuse;
    private JLabel mLabelCurrentUser;

    private JList mLastFocused;
    private ArrayList<User> users;

    private JPopupMenu mListAllPopUp;
    private JPopupMenu mListFriendsPopUp;

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
        reloadRequests();

        setButtonsEnabled(false);

        mLastFocused = mListAll;
        mLastFocused.setSelectedIndex(0);

        mListAll.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListAll) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    if (mLastFocused == mListRequests) {
                        setButtonsEnabled(false);
                    }
                    mLastFocused = mListAll;
                }
            }
        });

        mListFriends.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListFriends) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    if (mLastFocused == mListRequests) {
                        setButtonsEnabled(false);
                    }
                    mLastFocused = mListFriends;
                }
            }
        });

        mListRequests.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListRequests) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    mLastFocused = mListRequests;
                    setButtonsEnabled(true);
                }
            }
        });

        mListAllPopUp = new JPopupMenu();
        mListAllPopUp.add(new JMenuItem("Додати до друзів"));

        mListFriendsPopUp = new JPopupMenu();
        mListFriendsPopUp.add(new JMenuItem("Видалити із друзів"));

        mListAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pop(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pop(e);
            }

            private void pop(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    Component cmp = e.getComponent();
                    int x = e.getX();
                    int y = e.getY();
                    mListAll.setSelectedIndex(mListAll.locationToIndex(e.getPoint()));
                    mListAllPopUp.show(cmp, x, y);
                }
            }
        });

        mListFriends.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pop(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pop(e);
            }

            private void pop(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    Component cmp = e.getComponent();
                    int x = e.getX();
                    int y = e.getY();
                    mListFriends.setSelectedIndex(mListAll.locationToIndex(e.getPoint()));
                    mListFriendsPopUp.show(cmp, x, y);
                }
            }
        });

    }

    private void setButtonsEnabled(boolean isEnabled) {
        mButtonAccept.setEnabled(isEnabled);
        mButtonRefuse.setEnabled(isEnabled);
    }

    private void reloadAllUsers() {
        String currentID = AppConst.getCurrentUser().getId().toString();
        String sqlQuery = "SELECT a.* FROM USER_TBL a WHERE ( (a.user_id != \"" + currentID + "\") AND " +
                "( a.user_id NOT IN  ( SELECT b.second_id FROM FRIEND_RELATION_TBL b where b.first_id = \"" +
                currentID + "\" UNION SELECT c.first_id FROM FRIEND_RELATION_TBL c where c.second_id = \"" +
                currentID + "\")));";
        reloadUsers(
                sqlQuery,
                mListAll);
    }

    private void reloadFriends() {

        String currentID = AppConst.getCurrentUser().getId().toString();
        String sqlQuery = "SELECT b.* FROM  FRIEND_RELATION_TBL a " +
                " JOIN USER_TBL b ON ( (a.first_id = b.user_id AND a.second_id = \"" + currentID + "\") " +
                " OR ( a.second_id = b.user_id AND a.first_id = \"" + currentID + "\" ));";
        reloadUsers(
                sqlQuery,
                mListFriends);
    }

    private void reloadRequests() {
        String currentID = AppConst.getCurrentUser().getId().toString();
        String sqlQuery = "SELECT b.* FROM  REQUEST_TBL a " +
                " JOIN USER_TBL b ON (a.sender_id = b.user_id AND a.receiver_id = \"" + currentID + "\");";
        reloadUsers(
                sqlQuery,
                mListRequests);
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
                new LoginFrame();
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
