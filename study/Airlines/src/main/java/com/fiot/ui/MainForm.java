package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.app.model.Airport;
import com.fiot.app.model.Flight;
import com.fiot.app.model.Order;
import com.fiot.app.model.User;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;
import com.fiot.dao.exception.DAOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Клас інтерфейсу, відповідає за головне вікно
 */
public class MainForm extends JFrame {
    private JTabbedPane mTabbedPane1;
    private JPanel rootPane;
    private JComboBox mComboBoxDepAir;
    private JComboBox mComboBoxArrAir;
    private JComboBox mComboBoxClass;
    private JComboBox mComboBoxPrice;
    private JButton mButtonFind;
    private JTextField mTextFieldFirstName;
    private JTextField mTextFieldSecondName;
    private JTextField mTextFieldEmail;
    private JPasswordField mPasswordField1;
    private JFormattedTextField mFormattedTextFieldPhone;
    private JTextField mTextFieldAddress;
    private JButton mButtonSave;
    private JButton mButtonAddFlight;
    private JList mListOrdered;
    private JList mListAll;
    private JButton mButtonUpdate;

    private JPopupMenu mJPopupMenu;

    private java.util.List<Airport> mAirports;
    private java.util.Map<UUID, Airport> mapAir;

    /**
     * Конструктор, створює компоненти
     */
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

    /**
     * ініціалізує компоненти
     */
    private void init() {

        addJMenu();
        try {
            mAirports = AppConst.DAO.select(Airport.class, "");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        mapAir = new HashMap<>();
        for (Airport a : mAirports) {
            mapAir.put(a.getId(), a);
        }

        mComboBoxArrAir.addItem("Усі");
        mComboBoxDepAir.addItem("Усі");
        for (Airport a : mAirports) {
            mComboBoxArrAir.addItem(a);
            mComboBoxDepAir.addItem(a);
        }

        mComboBoxClass.addItem("Економ");
        mComboBoxClass.addItem("Бізнес");
        mComboBoxClass.addItem("Змішаний");


        mComboBoxClass.setSelectedIndex(0);
        mComboBoxArrAir.setSelectedIndex(0);
        mComboBoxDepAir.setSelectedIndex(0);

        mButtonAddFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FlightInfoForm(mAirports, null);
            }
        });

        User user = AppConst.getCurrentUser();

        mTextFieldFirstName.setText(user.getFirstName());
        mTextFieldSecondName.setText(user.getSecondName());

        mTextFieldEmail.setText(user.getEmail());
        mPasswordField1.setText(user.getPassword());

        mTextFieldAddress.setText(user.getAddress());
        mFormattedTextFieldPhone.setText(user.getPhone());

        mButtonFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String c1 = "";
                String c2 = "";

                if (mComboBoxDepAir.getSelectedIndex() != 0) {
                    Airport f = (Airport) mComboBoxDepAir.getSelectedItem();
                    c1 = " and dep_air_id = \"" + f.getId() + "\" ";
                }
                if (mComboBoxArrAir.getSelectedIndex() != 0) {
                    Airport f = (Airport) mComboBoxArrAir.getSelectedItem();
                    c1 = " and arr_air_id = \"" + f.getId() + "\" ";
                }
                long lower = 0;
                long upper = Long.MAX_VALUE;

                switch (mComboBoxPrice.getSelectedIndex()) {
                    case 0:
                        upper = 300;
                        break;
                    case 1:
                        lower = 300;
                        upper = 700;
                        break;
                    case 2:
                        lower = 700;
                        break;
//                    default:
//                        throw new IllegalArgumentException("must be only 3 items");
                }

                String sql = "select * from FLIGHT_TBL where (class_type = \"" + mComboBoxClass.getSelectedItem().toString() + "\""
                        + " and price >= " + lower + " and price < " + upper + " " + c1 + " " + c2 + " );";

                reloadFlights(sql, mListAll);
            }
        });


/************************************************************************************************/
        mJPopupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Замовити");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flight flight = (Flight) mListAll.getSelectedValue();

                if (flight == null) {
                    return;
                }

                Order order = new Order(
                        UUID.randomUUID(),
                        AppConst.getCurrentUser().getId(),
                        flight.getId()
                );

                try {
                    AppConst.DAO.insert(order);
                } catch (DAOException e1) {
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(
                        MainForm.this,
                        "Ви замовили білет на рейс \n" + flight.toString()
                                + "\nПеревірте персональну скриньку для детальнішої інформації",
                        "замовлення білету",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        mJPopupMenu.add(item);


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
                    mJPopupMenu.show(cmp, x, y);
                }
            }
        });
        /*********************************************************************/


        mButtonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadOrdered();
            }
        });

        reloadOrdered();

        mButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Context c = new Context();
                c.put("id", AppConst.getCurrentUser().getId());
                c.put("first_name", mTextFieldFirstName.getText().trim());
                c.put("second_name", mTextFieldSecondName.getText().trim());
                c.put("email", mTextFieldEmail.getText().trim());
                c.put("password", mPasswordField1.getText().trim());
                c.put("phone", mFormattedTextFieldPhone.getText().trim());
                c.put("address", mTextFieldAddress.getText().trim());

                try {
                    CommandExecutor.getInstance().execute("updateUser", c, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    /**
     * функція завантажує замовлення
     */
    private void reloadOrdered() {

        String sql = "select b.* from ORDER_TBL a JOIN FLIGHT_TBL b on ( (a.flight_id = b.flight_id) "
                + " and ( a.user_id = \"" + AppConst.getCurrentUser().getId() + "\"));";
        reloadFlights(sql, mListOrdered);
    }


    /**
     * функція завантадує рейси
     * @param sqlQuery sql-запит для виконання
     * @param jList список, куди завантажити рейси
     */
    private void reloadFlights(String sqlQuery, final JList jList) {
        final ArrayList<Flight> list = new ArrayList<>();
        Context c = new Context();
        c.put("list", list);
        c.put("sqlQuery", sqlQuery);
        try {
            CommandExecutor.getInstance().execute("loadFlights", c, new Runnable() {
                @Override
                public void run() {

                    for (Flight f : list) {
                        f.setArrAirport(mapAir.get(f.getArrAirportID()));
                        f.setDepAirport(mapAir.get(f.getDepAirportID()));
                    }
                    DefaultListModel<Flight> model = new DefaultListModel<>();
                    for (Flight f : list) {
                        model.addElement(f);
                    }
                    jList.setModel(model);
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
                        "Тут знаходиться інформація про компанію !",
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
                        "Тут ви можете знайти інформацію про всі необхідні документи !",
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
                        "Тут ви можете знайти інформацію про вартість послуг",
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
                        "Тут знаходиться необхідна інформація про правила перевезення дітей",
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
                        "Якщо хочете прочитати детальну інформацію про перевезення тварин, \nзаходьте сюди ! ",
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
                        "Про перевезення людей із особливими потребами, почитайте тут ! ",
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
