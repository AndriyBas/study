package com.oyster.ui;

import com.oyster.app.AppConst;
import com.oyster.app.model.*;
import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;
import com.oyster.core.controller.command.TestCommand;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.ui.dialogs.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


/**
 * Клас відповідає табові, що реалізує вікно кправління профілями
 *
 * @author bamboo
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
    private JList mListTab2Group;
    private JButton mTestButton;

    private IProfile currentPerson;

    private DocumentListener mDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            act();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            act();
        }


        @Override
        public void changedUpdate(DocumentEvent e) {
            act();
        }

        private void act() {
            mButtonSave.setEnabled(true);
        }
    };

    private HistoryTab historyTab;
    private ScheduleTab scheduleTab;


    /**
     * конструктор, створює елементи інтерфейсу
     */
    public MainForm() {
        super((String) AppConst.APP_CONFIG.getValue("progTitle"));

        add(rootPanel);

        int width = (Integer) AppConst.APP_CONFIG.getValue("mainScreenWidth");
        int height = (Integer) AppConst.APP_CONFIG.getValue("mainScreenHeight");

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        hardCoreInit();

        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setVisible(true);
    }


    /**
     * ініціалізує елементи інтерфейсу
     */
    private void hardCoreInit() {

        mTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Random r = new Random();
                char a = (char) ('a' + r.nextInt(26));
                Context context = new Context();
                context.put("power", 10);
                new TestCommand(String.valueOf(a)).execute(context);
            }
        });

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

        mTextFieldInfo1.getDocument().addDocumentListener(mDocumentListener);
        mTextFieldInfo2.getDocument().addDocumentListener(mDocumentListener);
        mTextFieldInfo3.getDocument().addDocumentListener(mDocumentListener);
        mTextFieldInfo4.getDocument().addDocumentListener(mDocumentListener);
        mTextFieldInfo5.getDocument().addDocumentListener(mDocumentListener);
        mTextFieldInfo6.getDocument().addDocumentListener(mDocumentListener);
        mTextFieldInfo7.getDocument().addDocumentListener(mDocumentListener);
        mPasswordField1.getDocument().addDocumentListener(mDocumentListener);

        mButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonClick();
            }
        });

        mButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButtonClick();
            }
        });

        scheduleTab = new ScheduleTab(this, mComboBoxScheduleFaculty, mListTab2Group, mTable1);

        historyTab = new HistoryTab(this, mComboBoxAllHistory, mListAllHistory);
    }

    /**
     * метод спрацьовує при натисненні на кнопку "Видалити"
     */
    private void deleteButtonClick() {

        if (currentPerson == null) {
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Підтвердити видалення акаунту  : ",
                "Захист від дурака",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (dialogResult == JOptionPane.CANCEL_OPTION) {
            return;
        }

        Context c = new Context();
        c.put("profile", currentPerson);

        try {
            CommandExecutor.getInstance().execute("deleteIProfile", c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentPerson = null;

        comboBoxChangeAction();
    }

    /**
     * метод спрацьовує при натисненні на кнопку "Зберегти"
     */
    private void saveButtonClick() {

        if (currentPerson == null) {
            return;
        }

        if (currentPerson instanceof Admin) {
            Admin a = (Admin) currentPerson;
            saveUpdate(a.getProfile());
            saveUpdate(a.getWorkerInfo());

        } else if (currentPerson instanceof Teacher) {
            Teacher t = (Teacher) currentPerson;
            saveUpdate(t.getProfile());
            saveUpdate(t.getWorkerInfo());

        } else if (currentPerson instanceof Student) {
            Student s = (Student) currentPerson;
            saveUpdate(s);
        }
        updateUI();
    }


    /**
     * зберігає інформацію про профіль студента
     *
     * @param s екземпляр класу Student
     */
    private void saveUpdate(Student s) {
        saveUpdate(s.getProfile());

        String course = mTextFieldInfo5.getText().trim();
        String bookNum = mTextFieldInfo7.getText().trim();

        String facultyName = mTextFieldInfo4.getText().trim();
        String groupName = mTextFieldInfo6.getText().trim();

        s.setCourse(Integer.parseInt(course));
        s.setBookNum(Integer.parseInt(bookNum));

        s.getGroup().setName(groupName);
        s.getGroup().getFaculty().setName(facultyName);

        try {
            AppConst.DAO.update(s);
            AppConst.DAO.update(s.getGroup());
            AppConst.DAO.update(s.getGroup().getFaculty());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * зберігає інформацію про профіль
     *
     * @param p екземпляр класу Profile
     */
    private void saveUpdate(Profile p) {
        String firstName = mTextFieldInfo1.getText().trim();
        String secondName = mTextFieldInfo2.getText().trim();
        String password = mPasswordField1.getText().trim();
        String birthday = mTextFieldInfo3.getText().trim();
        Long birthdayLong = 0L;
        try {
            Date d = (Date) new DateFormatter(AppConst.DATE_FORMAT).stringToValue(birthday);
            birthdayLong = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        p.setFirstName(firstName);
        p.setSecondName(secondName);
        p.setBirthday(birthdayLong);
        p.setPassword(password);
        try {
            AppConst.DAO.update(p);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * зберігає інформацію про роботу
     *
     * @param wi екземпляр класу WorkerInfo
     */
    private void saveUpdate(WorkerInfo wi) {
        String position = mTextFieldInfo4.getText().trim();
        String salary = mTextFieldInfo5.getText().trim();
        String dateHiredStr = mTextFieldInfo6.getText().trim();
        Long dateHired = 0L;
        try {
            Date d = (Date) new DateFormatter(AppConst.DATE_FORMAT).stringToValue(dateHiredStr);
            dateHired = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        wi.setPosition(position);
        wi.setSalary(Integer.parseInt(salary));
        wi.setDateHired(dateHired);
        try {
            AppConst.DAO.update(wi);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * оновлює інтерфейс
     */
    private void updateUI() {
        mButtonSave.setEnabled(false);
        validate();
        repaint();
    }

    /**
     * метод спрацьовує при зміні факультету
     */
    private void facultyChangedAction() {
        final Faculty f = (Faculty) mComboBoxProfileStudentTypeFaculty.getSelectedItem();
        java.util.List<Group> groups = null;
        try {
            groups = AppConst.DAO.select(Group.class, new DAOFilter() {
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
        updateUI();
    }

    /**
     * метод спрацьовує при зміні групи
     */
    private void groupChangedAction() {
        final Group group = (Group) mComboBoxProfileStudentTypeGroup.getSelectedItem();
        java.util.List<Student> students = null;
        try {
            students = AppConst.DAO.select(Student.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Student s = (Student) entity;
                    return s.getGroupId().equals(group.getId());
                }
            });
            for (Student student : students) {
                student.setProfile((Profile) AppConst.DAO.read(Profile.class, student.getProfileId()));
                student.setGroup(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showFilteredStudents(students);
    }

    /**
     * метод відображає список студентів, що пройшли фільтри, встановлені користувачем
     *
     * @param students список студентів
     */
    private void showFilteredStudents(java.util.List<Student> students) {
        DefaultListModel<Student> model = new DefaultListModel<Student>();
        for (Student s : students) {
            model.addElement(s);
        }
        mListPeople.setModel(model);

        if (students.size() > 0) {
            mListPeople.setSelectedIndex(0);
        }
        updateUI();
    }

    /**
     * метод спрацьовує при зміні типу користувача
     */
    private void comboBoxChangeAction() {

        mButtonSave.setEnabled(false);
        int selected = mComboBoxProfileType.getSelectedIndex();
        if (selected < 3) {
            mComboBoxProfileStudentTypeFaculty.setVisible(false);
            mComboBoxProfileStudentTypeGroup.setVisible(false);
            mLable7.setVisible(false);
            mTextFieldInfo7.setVisible(false);
            mLable4.setText("Посада");
            mLabel5.setText("Зарплата");
            mLable6.setText("Працює із");

        } else {

            mComboBoxProfileStudentTypeFaculty.setVisible(true);
            mComboBoxProfileStudentTypeGroup.setVisible(true);
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
                    admins = AppConst.DAO.select(Admin.class, "");
                    for (Admin a : admins) {
                        a.setProfile((Profile) AppConst.DAO.read(Profile.class, a.getProfileId()));
                        a.setWorkerInfo((WorkerInfo) AppConst.DAO.read(WorkerInfo.class, a.getWorkerInfoId()));
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
                    teachers = AppConst.DAO.select(Teacher.class, "");
                    for (Teacher teacher : teachers) {
                        teacher.setProfile((Profile) AppConst.DAO.read(Profile.class, teacher.getProfileId()));
                        teacher.setWorkerInfo((WorkerInfo) AppConst.DAO.read(WorkerInfo.class, teacher.getWorkerInfoId()));
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
                    faculties = AppConst.DAO.select(Faculty.class, "");
                } catch (DAOException e) {
                    e.printStackTrace();
                }

                mComboBoxProfileStudentTypeFaculty.setModel(new DefaultComboBoxModel(faculties.toArray()));

                mComboBoxProfileStudentTypeFaculty.setSelectedIndex(0);

                break;
        }
        updateUI();
    }

    /**
     * метод заповнює інформацію про профіль
     *
     * @param profile екемпляр класу Profile
     */
    private void fillInfoFields(Profile profile) {
        mTextFieldInfo1.setText(profile.getFirstName());
        mTextFieldInfo2.setText(profile.getSecondName());
        mTextFieldInfo3.setText(AppConst.DATE_FORMAT.format(new Date(profile.getBirthday())));
        mPasswordField1.setText(profile.getPassword());

        loadHistory(profile);
    }

    /**
     * метод завантажує історію вибраного користувача
     *
     * @param profile користувач
     */
    private void loadHistory(final Profile profile) {

        final java.util.List<History> histories = new ArrayList<History>();

        Context c = new Context();
        c.put("list", histories);
        c.put("sqlQuery", "select * from HISTORY_TBL where author_id = \"" +
                profile.getId() + "\";");

        try {
            CommandExecutor.getInstance().execute("loadHistory", c, new Runnable() {
                @Override
                public void run() {
                    for (History h : histories) {
                        h.setAuthor(profile);
                    }
                    DefaultListModel<History> historyModel = new DefaultListModel<>();
                    for (History h : histories) {
                        historyModel.addElement(h);
                    }

                    mListHistoryProfile.setModel(historyModel);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * метод заповнює інформацію про роботу
     *
     * @param workerInfo екемпляр класу WorkerInfo
     */
    private void fillInfoFields(WorkerInfo workerInfo) {
        mTextFieldInfo4.setText(workerInfo.getPosition());
        mTextFieldInfo5.setText(String.valueOf(workerInfo.getSalary()));
        mTextFieldInfo6.setText(AppConst.DATE_FORMAT.format(new Date(workerInfo.getDateHired())));

    }

    /**
     * метод заповнює інформацію про адміністратора
     *
     * @param admin екемпляр класу Admin
     */
    private void fillInfoFields(Admin admin) {
        fillInfoFields(admin.getProfile());
        fillInfoFields(admin.getWorkerInfo());

        currentPerson = admin;
        updateUI();
    }

    /**
     * метод заповнює інформацію про викладача
     *
     * @param teacher екемпляр класу Teacher
     */
    private void fillInfoFields(Teacher teacher) {
        fillInfoFields(teacher.getProfile());
        fillInfoFields(teacher.getWorkerInfo());

        currentPerson = teacher;
        updateUI();
    }

    /**
     * метод заповнює інформацію про студента
     *
     * @param student екемпляр класу Student
     */
    private void fillInfoFields(Student student) {
        fillInfoFields(student.getProfile());
        mTextFieldInfo4.setText(student.getGroup().getFaculty().getName());
        mTextFieldInfo5.setText(String.valueOf(student.getCourse()));
        mTextFieldInfo6.setText(student.getGroup().getName());
        mTextFieldInfo7.setText(String.valueOf(student.getBookNum()));

        currentPerson = student;
        updateUI();
    }

    /**
     * метод спрацьовує при натисненні на клавішу "Додати аккаунт"
     */
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

        if ((s != null) && (s.length() > 0)) {

            JDialog dialog = null;
            switch (s) {
                case "Студент":

                    dialog = new NewStudentCustomDialog(this);
                    break;

                case "Виладач":
                    dialog = new NewTeacherCustomDialog(this);
                    break;

                case "Адміністратор":

                    dialog = new NewAdminCustomDialog(this);
                    break;

                case "Група":

                    dialog = new NewGroupCustomDialog(this);
                    break;

                case "Факультет":

                    dialog = new NewFacultyCustomDialog(this);
                    break;

                case "Предмет":

                    dialog = new NewSubjectCustomDialog(this);
                    break;
            }
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    /**
     * ініціалізує меню у програмі
     */
    private void addJMenu() {

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Файл");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        menuItem = new JMenuItem("Додати акаунт",
                KeyEvent.VK_N
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
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        MainForm.this,
                        "Це посібник користувача",
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
                        MainForm.this,
                        "Автор :\t\tБас Андрій\nВерсія :\t\t1.3",
                        "Про програму",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(menuItem);

        setJMenuBar(menuBar);
    }
}
