package com.oyster.ui;

import com.oyster.app.AppConst;
import com.oyster.app.model.*;
import com.oyster.core.controller.command.Context;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.dao.impl.DAOCRUDJdbc;
import com.oyster.ui.dialogs.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.UUID;


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
    private JScrollPane mScrollPanePeople;

    public MainForm() {
        super("KPI City");
//        setContentPane(rootPane);

        add(rootPanel);
        setPreferredSize(new Dimension(750, 480));
        setMinimumSize(new Dimension(750, 480));

        hardCoreInit();

        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setVisible(true);
    }


    private void hardCoreInit() {

//        mTextFieldInfo3 = new JFormattedTextField(AppConst.dateFormat);
        addJMenu();

        mButtonNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });

        comboBoxChangeAction();
        mComboBoxProfileType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxChangeAction();
            }
        });

        mComboBoxAllHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxHistoryChangeAction();
            }
        });

        mListPeople.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object o = mListPeople.getSelectedValue();
                if (o instanceof Admin) {
                    fillInfoFields((Admin) o);
                } else if (o instanceof Teacher) {
                    fillInfoFields((Teacher) o);
                } else if (o instanceof Student) {
                    fillInfoFields((Student) o);
                }
            }
        });

        mComboBoxProfileStudentTypeFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facultyChangedAction();
            }
        });

        mComboBoxProfileStudentTypeGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupChangedAction();
            }
        });

    }


    private void comboBoxHistoryChangeAction() {

    }

    private void facultyChangedAction() {

        final Faculty f = (Faculty) mComboBoxProfileStudentTypeFaculty.getSelectedItem();

        DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(AppConst.context);
        java.util.List<Group> groups = null;
        try {
            groups = x.select(Group.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Group g = (Group) entity;
                    return g.getFacultyId().equals(f.getId());
                }
            });
            for (Group g : groups) {
                g.setFaculty(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mComboBoxProfileStudentTypeGroup.setModel(new DefaultComboBoxModel(groups.toArray()));
        if (groups.size() > 0) {
            mComboBoxProfileStudentTypeGroup.setSelectedIndex(0);
        }

        validate();
        repaint();
    }

    private void groupChangedAction() {



        final Group group = (Group) mComboBoxProfileStudentTypeGroup.getSelectedItem();

        DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(AppConst.context);
        java.util.List<Student> students = null;
        try {
            students = x.select(Student.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Student s = (Student) entity;
                    return s.getGroupId().equals(group.getId());
                }
            });
            for (Student student : students) {
                student.setProfile((Profile) x.read(Profile.class, student.getProfileId()));
                student.setGroup(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showFilteredStudents(students);
    }

    private void showFilteredStudents(java.util.List<Student> students) {

        DefaultListModel<Student> model = new DefaultListModel<Student>();
        for (Student s : students) {
            model.addElement(s);
        }
        mListPeople.setModel(model);

        if (students.size() > 0) {
            mListPeople.setSelectedIndex(0);
        }

        validate();
        repaint();
    }

    private void comboBoxChangeAction() {

        mButtonSave.setEnabled(false);
        int selected = mComboBoxProfileType.getSelectedIndex();

        DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(AppConst.context);

        if (selected < 3) {
            mComboBoxProfileStudentTypeFaculty.setVisible(false);
            mComboBoxProfileStudentTypeGroup.setVisible(false);
            mButtonRating.setEnabled(false);
            mLable7.setVisible(false);
            mTextFieldInfo7.setVisible(false);
            mLable4.setText("Посада");
            mLabel5.setText("Зарплата");
            mLable6.setText("Працює із");

        } else {

            mComboBoxProfileStudentTypeFaculty.setVisible(true);
            mComboBoxProfileStudentTypeGroup.setVisible(true);
            mButtonRating.setEnabled(true);
            mLable7.setVisible(true);
            mTextFieldInfo7.setVisible(true);
            mLable4.setText("Факультет");
            mLabel5.setText("Курс");
            mLable6.setText("Група");
            mLable7.setText("НЗК");

        }

        switch (mComboBoxProfileType.getSelectedIndex()) {
            case 0:


                mScrollPanePeople.setVisible(false);
                mButtonDelete.setEnabled(false);

                fillInfoFields(AppConst.getCurrentAdmin());


                break;
            case 1:

                mScrollPanePeople.setVisible(true);
                mButtonDelete.setEnabled(true);

                java.util.List<Admin> admins = null;

                try {
                    admins = x.select(Admin.class, "");
                    for (Admin a : admins) {
                        a.setProfile((Profile) x.read(Profile.class, a.getProfileId()));
                        a.setWorkerInfo((WorkerInfo) x.read(WorkerInfo.class, a.getWorkerInfoId()));
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                }

                DefaultListModel<Admin> model = new DefaultListModel<>();
                for (Admin a : admins) {
                    model.addElement(a);
                }
                mListPeople.setModel(model);

                mListPeople.setSelectedIndex(0);

                break;

            case 2:


                mScrollPanePeople.setVisible(true);
                mButtonDelete.setEnabled(true);

                java.util.List<Teacher> teachers = null;

                try {
                    teachers = x.select(Teacher.class, "");
                    for (Teacher teacher : teachers) {
                        teacher.setProfile((Profile) x.read(Profile.class, teacher.getProfileId()));
                        teacher.setWorkerInfo((WorkerInfo) x.read(WorkerInfo.class, teacher.getWorkerInfoId()));
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                }

                DefaultListModel<Teacher> teacherModel = new DefaultListModel<>();
                for (Teacher teacher : teachers) {
                    teacherModel.addElement(teacher);
                }
                mListPeople.setModel(teacherModel);
                mListPeople.setSelectedIndex(0);


                break;

            case 3:

                mScrollPanePeople.setVisible(true);
                mButtonDelete.setEnabled(true);

                java.util.List<Faculty> faculties = null;

                try {
                    faculties = x.select(Faculty.class, "");
                } catch (DAOException e) {
                    e.printStackTrace();
                }

                mComboBoxProfileStudentTypeFaculty.setModel(new DefaultComboBoxModel(faculties.toArray()));

                mComboBoxProfileStudentTypeFaculty.setSelectedIndex(0);

                break;
        }
        validate();
        repaint();
    }

    private void fillInfoFields(Profile profile) {
        mTextFieldInfo1.setText(profile.getName());
        mTextFieldInfo2.setText(profile.getSurname());
        mTextFieldInfo3.setText(AppConst.dateFormat.format(new Date(profile.getBirthday())));
        mPasswordField1.setText(profile.getPassword());
    }

    private void fillInfoFields(WorkerInfo workerInfo) {
        mTextFieldInfo4.setText(workerInfo.getPosition());
        mTextFieldInfo5.setText(String.valueOf(workerInfo.getSalary()));
        mTextFieldInfo6.setText(AppConst.dateFormat.format(new Date(workerInfo.getDateHired())));

    }

    private void fillInfoFields(Admin admin) {
        fillInfoFields(admin.getProfile());
        fillInfoFields(admin.getWorkerInfo());
    }

    private void fillInfoFields(Teacher teacher) {
        fillInfoFields(teacher.getProfile());
        fillInfoFields(teacher.getWorkerInfo());
    }

    private void fillInfoFields(Student student) {
        fillInfoFields(student.getProfile());
        mTextFieldInfo4.setText(student.getGroup().getFaculty().getName());
        mTextFieldInfo5.setText(String.valueOf(student.getCourse()));
        mTextFieldInfo6.setText(student.getGroup().getName());
        mTextFieldInfo7.setText(String.valueOf(student.getBookNum()));
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
                    d.pack();
                    d.setVisible(true);
                    break;

                case "Виладач":

                    NewTeacherCustomDialog teach = new NewTeacherCustomDialog(this, this);
                    teach.pack();
                    teach.setVisible(true);
                    break;

                case "Адміністратор":

                    NewAdminCustomDialog adminDialog = new NewAdminCustomDialog(this, this);
                    adminDialog.pack();
                    adminDialog.setVisible(true);
                    break;

                case "Група":

                    NewGroupCustomDialog gr = new NewGroupCustomDialog(this, this);

                    gr.pack();
                    gr.setVisible(true);
                    break;

                case "Факультет":

                    NewFacultyCustomDialog fac = new NewFacultyCustomDialog(this, this);
                    fac.pack();
                    fac.setVisible(true);
                    break;

                case "Предмет":

                    NewSubjectCustomDialog sub = new NewSubjectCustomDialog(this, this);
                    sub.pack();
                    sub.setVisible(true);
                    sub.setVisible(true);
                    break;
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

        DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(AppConst.context);

        switch (action) {
            case "registerSubject":

                Subject subject = new Subject(UUID.randomUUID(), ((String) c.get("name")).toUpperCase());
                try {
                    x.insert(subject);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this, Utils.makePretty("Помилка створення предмету : \n" + e.getMessage()));
                }

                break;
            case "registerFaculty":

                Faculty fac = new Faculty(UUID.randomUUID(), ((String) c.get("name")).toUpperCase());
                try {
                    x.insert(fac);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this, Utils.makePretty("Помилка створення факультету : \n" + e.getMessage()));
                }


                break;
            case "registerGroup":

                final String facultyName = (String) c.get("faculty");
                java.util.List<Faculty> faculties = null;
                try {
                    faculties = x.select(Faculty.class, new DAOFilter() {
                        @Override
                        public <T> boolean accept(T entity) {
                            Faculty f = (Faculty) entity;
                            return f.getName().equals(facultyName);
                        }
                    });
                } catch (DAOException e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this, Utils.makePretty("Помилка зчитування факультету : \n" + e.getMessage()));
                }

                if (faculties.size() == 0) {
                    Utils.showErrorDialog(this, "Немає такого факультету!");
                    break;
                }

                Group g = new Group(UUID.randomUUID(),
                        faculties.get(0).getId(),
                        ((String) c.get("name")).toUpperCase());
                try {
                    x.insert(g);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this, Utils.makePretty("Помилка створення групи : \n" + e.getMessage()));
                }

                break;
            case "registerStudent":

                Profile pStudent = new Profile(
                        UUID.randomUUID(),
                        (String) c.get("name"),
                        (String) c.get("surname"),
                        (String) c.get("password"),
                        (Long) c.get("birthday")
                );

                final String groupName = (String) c.get("group");
                java.util.List<Group> groups = null;
                try {
                    groups = x.select(Group.class, new DAOFilter() {
                        @Override
                        public <T> boolean accept(T entity) {
                            Group g = (Group) entity;
                            return g.getName().equals(groupName);
                        }
                    });
                    for (Group gg : groups) {
                        gg.setFaculty((Faculty) x.read(Faculty.class, gg.getFacultyId()));
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this, Utils.makePretty("Помилка зчитування групи : \n" + e.getMessage()));
                }

                if (groups.size() == 0) {
                    Utils.showErrorDialog(this, "Немає такої групи!");
                    break;
                }

                final Group group = groups.get(0);

                if (!group.getFaculty().getName().equals((String) c.get("faculty"))) {
                    Utils.showErrorDialog(this, "Немає такої групи на цьому факультеті!");
                    break;
                }


                Student student = new Student(
                        UUID.randomUUID(),
                        pStudent.getId(),
                        groups.get(0).getId(),
                        (Integer) c.get("course"),
                        (Integer) c.get("bookNum")
                );

                try {
                    x.insert(pStudent);
                    x.insert(student);
                } catch (DAOException e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this, Utils.makePretty("Помилка створення студента : \n" + e.getMessage()));
                }

                break;

            case "registerTeacher":

                Profile pTeacher = new Profile(
                        UUID.randomUUID(),
                        (String) c.get("name"),
                        (String) c.get("surname"),
                        (String) c.get("password"),
                        (Long) c.get("birthday")
                );

                WorkerInfo wTeacher = new WorkerInfo(
                        UUID.randomUUID(),
                        (String) c.get("position"),
                        (Integer) c.get("salary"),
                        (Long) c.get("dateHired")
                );

                Teacher teacher = new Teacher(
                        UUID.randomUUID(),
                        pTeacher.getId(),
                        wTeacher.getId()
                );

                try {
                    x.insert(pTeacher);
                    x.insert(wTeacher);
                    x.insert(teacher);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this,
                            Utils.makePretty("Помилка створення викладача : \n" + e.getMessage()));
                }
                break;
            case "registerAdmin":

                Profile pAdmin = new Profile(
                        UUID.randomUUID(),
                        (String) c.get("name"),
                        (String) c.get("surname"),
                        (String) c.get("password"),
                        (Long) c.get("birthday")
                );

                WorkerInfo wAdmin = new WorkerInfo(
                        UUID.randomUUID(),
                        (String) c.get("position"),
                        (Integer) c.get("salary"),
                        (Long) c.get("dateHired")
                );

                Admin admin = new Admin(
                        UUID.randomUUID(),
                        pAdmin.getId(),
                        wAdmin.getId()
                );

                try {
                    x.insert(pAdmin);
                    x.insert(wAdmin);
                    x.insert(admin);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showErrorDialog(this,
                            Utils.makePretty("Помилка створення адміністратора : \n" + e.getMessage()));
                }
                break;
        }

    }
/*
    private String toUTF8(String s) {
        byte[] bytes = new byte[0];
        try {
            bytes = s.getBytes("UTF-8");
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
