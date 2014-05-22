package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.app.model.Message;
import com.fiot.app.model.User;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;
import com.fiot.dao.exception.DAOException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * клас відповідає за роботу інтерфейсу головного вікна програми
 * <p/>
 * Created by krabik on 15.04.14.
 */
public class GroupFrame extends JFrame {
    private JPanel rootPanel;

    private JList mListModers;
    private JList mListBlack;
    private JList mListPosts;
    private JList mListUsers;
    private JTextField mTextFieldPost;
    private JButton mButtonPost;

    private JList mLastFocused;

    private JPopupMenu mListPopUpWhiteUser;
    private JPopupMenu mListPopUpWhiteModer;
    private JPopupMenu mListPopUpBlack;
    private JPopupMenu mListPopUpPost;

    /**
     * конструктор, створює елементи інтерфейсу
     */
    public GroupFrame() {
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

    /**
     * ініціалізує елементи інтерфейсу
     */
    private void init() {

        addJMenu();

        AppConst.setCurrentAdmin(new User(
                UUID.fromString("45677e3c-24a9-4e31-997b-88084a9ccccc"),
                "admin",
                "admin",
                "admin@gmail.com",
                "password",
                "admin"
        ));

        mLastFocused = mListPosts;
        reloadUsers();
        reloadPosts();

        mButtonPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postMessage();
            }
        });

        mListPosts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListPosts) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    mLastFocused = mListPosts;
                }
            }
        });

        mListModers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListModers) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    mLastFocused = mListModers;
                }
            }
        });

        mListUsers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListUsers) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    mLastFocused = mListUsers;
                }
            }
        });

        mListBlack.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mLastFocused != mListBlack) {
                    mLastFocused.removeSelectionInterval(0, mLastFocused.getModel().getSize() - 1);
                    mLastFocused = mListBlack;
                }
            }
        });

        mListPopUpWhiteUser = new JPopupMenu();
        mListPopUpWhiteModer = new JPopupMenu();

        JMenuItem item = new JMenuItem("Зробити модератором");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus((User) mListUsers.getSelectedValue(), "moder");
                reloadUsers();
            }
        });
        mListPopUpWhiteUser.add(item);


        item = new JMenuItem("Звільнити модератора");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus((User) mListModers.getSelectedValue(), "user");
                reloadUsers();
            }
        });
        mListPopUpWhiteModer.add(item);

        item = new JMenuItem("Видалити");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser((User) mListUsers.getSelectedValue());
            }
        });
        mListPopUpWhiteUser.add(item);

        item = new JMenuItem("Видалити");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser((User) mListModers.getSelectedValue());
            }
        });
        mListPopUpWhiteModer.add(item);

        item = new JMenuItem("Додати в чорний список");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus((User) mListUsers.getSelectedValue(), "black");
                reloadUsers();
            }
        });
        mListPopUpWhiteUser.add(item);

        item = new JMenuItem("Додати в чорний список");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus((User) mListModers.getSelectedValue(), "black");
                reloadUsers();
            }
        });
        mListPopUpWhiteModer.add(item);

        mListPopUpBlack = new JPopupMenu();
        item = new JMenuItem("Пробачити");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus((User) mListBlack.getSelectedValue(), "user");
                reloadUsers();
            }
        });
        mListPopUpBlack.add(item);

        mListPopUpPost = new JPopupMenu();
        item = new JMenuItem("Видалити пост");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMessage((Message) mListPosts.getSelectedValue());
            }
        });
        mListPopUpPost.add(item);


        mListPosts.addMouseListener(new MouseAdapter() {
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
                    mListPosts.setSelectedIndex(mListPosts.locationToIndex(e.getPoint()));
                    if (mListPosts.getSelectedValue() != null) {
                        mListPopUpPost.show(cmp, x, y);
                    }
                }
            }
        });

        mListModers.addMouseListener(new MouseAdapter() {
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
                    mListModers.setSelectedIndex(mListModers.locationToIndex(e.getPoint()));
                    if (mListModers.getSelectedValue() != null) {
                        mListPopUpWhiteModer.show(cmp, x, y);
                    }
                }
            }
        });

        mListUsers.addMouseListener(new MouseAdapter() {
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
                    mListUsers.setSelectedIndex(mListUsers.locationToIndex(e.getPoint()));
                    if (mListUsers.getSelectedValue() != null) {
                        mListPopUpWhiteUser.show(cmp, x, y);
                    }
                }
            }
        });

        mListBlack.addMouseListener(new MouseAdapter() {
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
                    mListBlack.setSelectedIndex(mListBlack.locationToIndex(e.getPoint()));
                    if (mListBlack.getSelectedValue() != null) {
                        mListPopUpBlack.show(cmp, x, y);
                    }
                }
            }
        });

    }

    private void postMessage() {

        String msg = mTextFieldPost.getText().trim();
        if (msg.length() == 0) {
            return;
        }
        Message message = new Message(
                UUID.randomUUID(),
                AppConst.getCurrentAdmin().getId(),
                msg
        );
        try {
            AppConst.DAO.insert(message);
        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }
        reloadPosts();
    }

    private void deleteMessage(Message msg) {

        if (msg == null) {
            return;
        }

        try {
            AppConst.DAO.delete(msg);
        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }

        reloadPosts();
    }

    private void removeUser(User selectedUser) {
        if (selectedUser == null) {
            return;
        }
        int dialogResult = JOptionPane.showConfirmDialog(
                this,
                "Підтвердити видалення користувача " + selectedUser.toString() + " ?",
                "Попередження",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.NO_OPTION) {
            return;
        }

        Context c = new Context();
        c.put("userId", selectedUser.getId());
        try {
            CommandExecutor.getInstance().execute("removeUser", c, new Runnable() {
                @Override
                public void run() {
                    reloadUsers();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeStatus(User selectedUser, String newStatus) {
        if (selectedUser == null) {
            return;
        }
        Context c = new Context();
        c.put("userId", selectedUser.getId());
        c.put("status", newStatus);
        try {
            CommandExecutor.getInstance().execute("changeStatus", c, new Runnable() {
                @Override
                public void run() {
                    reloadUsers();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * завантажує список всіх користувачів, що не є друзями залогіненому користувачеві
     */
    private void reloadAllUsers() {
        String sqlQuery = "SELECT a.* FROM USER_TBL a WHERE ( a.status = \"user\");";
        reloadUsers(
                sqlQuery,
                mListUsers);
    }

    /**
     * завантажує список всіх користувачів, що є друзями залогіненому користувачеві
     */
    private void reloadModers() {
        String sqlQuery = "SELECT a.* FROM USER_TBL a WHERE ( a.status = \"moder\");";
        reloadUsers(
                sqlQuery,
                mListModers);
    }

    /**
     * завантажує список всіх користувачів, що відправили заявку на друзі залогіненому користувачеві
     */
    private void reloadBlack() {
        String sqlQuery = "SELECT a.* FROM USER_TBL a WHERE ( a.status = \"black\");";
        reloadUsers(
                sqlQuery,
                mListBlack);
    }

    /**
     * завантажує користувачів, виконуюючи sql-запит sqlQuery та відображає їх у
     * JList-елементі інтерфейсу jList
     *
     * @param sqlQuery sql-запит для виконання
     * @param jList    куди завантажувати користувачів
     */
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


    /**
     * оновлює інтерфкйс програми
     */
    public void reloadUsers() {
        reloadBlack();
        reloadModers();
        reloadAllUsers();
    }

    private void reloadPosts() {
        final ArrayList<Message> list = new ArrayList<>();
        Context c = new Context();
        c.put("list", list);
        c.put("sqlQuery", "select * from MESSAGE_TBL;");
        try {
            CommandExecutor.getInstance().execute("loadPosts", c, new Runnable() {
                @Override
                public void run() {
                    DefaultListModel<Message> model = new DefaultListModel<>();
                    for (Message u : list) {
                        model.addElement(u);
                    }
                    mListPosts.setModel(model);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ініціалізує меню у програмі
     */
    private void addJMenu() {

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Файл");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);


        menuItem = new JMenuItem("Додати акаунт");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog dialog = new RegisterUserDialog(GroupFrame.this);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        menu.add(menuItem);


        menuItem = new JMenuItem("Вихід");
        menuItem.setMnemonic(KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_W, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupFrame.this.dispose();
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
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        GroupFrame.this,
                        "Тут допомога !!!",
                        "Посібник користувача",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Про програму");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        GroupFrame.this,
                        "Автор : Матіос Євген\nВерсія : 1.0",
                        "Про програму",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        setJMenuBar(menuBar);
    }
}
