package com.oyster.ui;

import com.oyster.app.model.Faculty;
import com.oyster.app.model.Group;
import com.oyster.core.controller.command.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/**
 * @author bamboo
 * @since 4/13/14 8:10 PM
 */
public class MainForm extends JFrame {

    private JPanel rootPanel;
    private JTabbedPane mTabbedPaneMain;
    private JComboBox mComboBoxProfileType;
    private JComboBox mComboBoxProfileStudentTypeFaculty;
    private JList mListPeople;
    private JComboBox mComboBoxProfileStudentTypeGroup;
    private JPanel mPanelProfile;
    private JPanel mPanelLeftControl;
    private JButton mButtonRating;
    private JButton mButtonSave;
    private JButton mButtonDelete;
    private JList mListHistoryProfile;
    private JTextField mTextFieldInfo1;
    private JTextField mTextFieldInfo2;
    private JTextField mTextFieldInfo3;
    private JTextField mTextFieldInfo5;
    private JTextField mTextFieldInfo6;
    private JTextField mTextFieldInfo7;
    private JButton mButtonNewUser;
    private JComboBox mComboBoxAllHistory;
    private JList mListAllHistory;
    private JComboBox mComboBoxScheduleFaculty;
    private JScrollPane mListGroups;
    private JTable mTable1;
    private JLabel mLabel5;
    private JLabel mLable6;
    private JLabel mLable7;
    private JLabel mLable1;
    private JLabel mLable2;
    private JLabel mLable3;
    private JLabel mLablePhoto;
    private JLabel mLable8;
    private JPasswordField mPasswordField1;
    private JLabel mLable4;
    private JTextField mTextFieldInfo4;


    public MainForm() {
        super("KPI City");
//        setContentPane(rootPane);


        add(rootPanel);
        setPreferredSize(new Dimension(750, 450));
        setMinimumSize(new Dimension(750, 450));

        hardCoreInit();

        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setVisible(true);
    }


    private void hardCoreInit() {

        addJMenu();


        mButtonNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });

    }

    private void newUserAction() {
        Object[] possibilities = {"Студент", "Виладач", "Адміністратор", "Група", "Факультет", "Предмет"};
        String s = (String) JOptionPane.showInputDialog(
                MainForm.this,
                "Виберіть категорію, яку хочете створити:\n",
                "Новий акаунт",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "Студент");

//If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {

            switch (s) {
                case "Студент":


                    NewStudentCustomDialog d = new NewStudentCustomDialog(this, this);
                    d.setPreferredSize(new Dimension(350, 490));
                    d.setMinimumSize(new Dimension(350, 490));
                    d.pack();
                    d.setVisible(true);

                    return;
                case "Виладач":

                    return;
                case "Адміністратор":

                    return;

                case "Група":

                    NewGroupCustomDialog gr = new NewGroupCustomDialog(this, this);
                    gr.setPreferredSize(new Dimension(350, 190));
                    gr.setMinimumSize(new Dimension(350, 150));
                    gr.pack();
                    gr.setVisible(true);
                    return;

                case "Факультет":


                    NewFacultyCustomDialog fac = new NewFacultyCustomDialog(this, this);
                    fac.setPreferredSize(new Dimension(350, 140));
                    fac.setMinimumSize(new Dimension(350, 140));
                    fac.pack();
                    fac.setVisible(true);
                    return;

                case "Предмет":

                    NewSubjectCustomDialog sub = new NewSubjectCustomDialog(this, this);
                    sub.setPreferredSize(new Dimension(350, 140));
                    sub.setMinimumSize(new Dimension(350, 140));
                    sub.pack();
                    sub.setVisible(true);
                    return;

            }
        }

    }


    private void addJMenu() {
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("Файл");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

//a group of JMenuItems
        menuItem = new JMenuItem("Додати акаунт",
                KeyEvent
                        .VK_N
        );
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });
        menu.add(menuItem);


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


    public void performAction(String action, Context c) {

    }

    public void createNew(Group g) {
        System.out.print("Create : ");
        System.out.println(g.getName());
    }

    public void createNew(Faculty f) {
        System.out.print("Create : ");
        System.out.println(f.getName());
    }


}
