package com.oyster.ui;

import com.oyster.app.AppConst;
import com.oyster.app.model.*;
import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Клас відповідає табові, що відображає розклад груп
 */
public class ScheduleTab {

    private JFrame frame;
    private JComboBox comboBoxFaculty;
    private JComboBox comboBoxSubject;
    private JComboBox comboBoxTeacher;
    private JList groupList;
    private JTable table;
    private java.util.List<Subject> subjects = null;
    private java.util.List<Teacher> teachers = null;
    private boolean DEBUG = false;
    private String[] daysOfWeek = new String[]{"Понеділок", "Вівторок", "Середа", "Четвер", "П’ятниця", "Субота"};
    private ArrayList<Object[]> data;
    private String[] columnNames = {"Пара",
            "Предмет",
            "Викладач",
            "Аудиторія",
    };
    private ArrayList<Classes> classes;
    private Teacher emptyTeacher;
    private Subject emptySubject;
    /**
     * Конструктор, що приймає елементи інтерфейсу для подальших операцій із ними
     *
     * @param frame вікно програми
     * @param comboBoxFaculty вибірка факультетів
     * @param groupList список груп
     * @param table таблиця розкладу
     */
    public ScheduleTab(JFrame frame, JComboBox comboBoxFaculty, JList groupList, JTable table) {
        this.frame = frame;
        this.comboBoxFaculty = comboBoxFaculty;
        this.groupList = groupList;
        this.table = table;

        hardcoreInit();
    }

    /**
     * ініціалізує компоненти
     */
    private void hardcoreInit() {
        emptyTeacher = new Teacher();
        emptyTeacher.setProfile(new Profile());

        emptySubject = new Subject();
        emptySubject.setName("");

        comboBoxFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facultyChangedAction();
            }
        });

        groupList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                reloadTable();
            }
        });

        reloadAll();
    }

    /**
     * перевіряє змінений елемент рзкладу та зберігає/видаляє його за потреби
     * @param c елемент розкладу
     */
    private void checkAndSave(Classes c) {
        try {
            AppConst.DAO.replace(c);

            Group g = (Group) groupList.getSelectedValue();

            AppConst.SESSION_TIME++;

            if (AppConst.SESSION_TIME > 20) {
                History h = new History(
                        UUID.randomUUID(),
                        AppConst.getCurrentAdmin().getProfileId(),
                        "Вніс зміни до розкладу групи " + g.getName()
                );
                AppConst.DAO.insert(h);
                AppConst.SESSION_TIME = 0;
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * реагує на зміну розкладу
     * @param e подія
     */
    private void tableValueChangedAction(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE
                | e.getType() == TableModelEvent.INSERT
                ) {

            Group g = (Group) groupList.getSelectedValue();

            int row = e.getFirstRow();
            int column = e.getColumn();

            Absence absence = null;

            Classes c = classes.get(row);
            if (c == null) {
                c = new Classes();
                c.setId(UUID.randomUUID());
                absence = new Absence();
                absence.setId(UUID.randomUUID());
                absence.setGroupId(g.getId());
                absence.setClassId(c.getId());
                c.setTime(row);

                try {
                    AppConst.DAO.insert(absence);
                } catch (DAOException e1) {
                    e1.printStackTrace();
                }

                classes.set(row, c);
            }
            switch (column) {
                case 1:
                    Subject s = (Subject) table.getModel().getValueAt(row,
                            column);
                    c.setSubject(s);
                    c.setSubjectId(s.getId());

                    checkAndSave(c);
                    break;

                case 2:

                    Teacher t = (Teacher) table.getModel().getValueAt(row,
                            column);

                    c.setTeacher(t);
                    c.setTeacherId(t.getId());

                    checkAndSave(c);

                    break;

                case 3:
                    Integer i = null;
                    try {
                        i = Integer.parseInt(table.getModel().getValueAt(row, column).toString());
                        if (i < 0) {
                            throw new IllegalArgumentException("Аудиторія повинна бути >= 0");
                        }
                    } catch (NumberFormatException ne) {
                        // ok, empty string then
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Utils.showErrorDialog(frame, Utils.makePretty(ex.getMessage()));
                        if (absence != null) {
                            try {
                                AppConst.DAO.delete(absence);
                            } catch (DAOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        return;
                    }
                    c.setAudience(i);

                    checkAndSave(c);

                    break;
            }
            System.out.println("Cell " + e.getFirstRow() + ", "
                    + e.getColumn() + " changed. The new value: "
                    + table.getModel().getValueAt(e.getFirstRow(),
                    e.getColumn()));
        }
    }


    /**
     * заповнює таблицю розкладу
     */
    private void fillTable() {

        final java.util.List<Classes> classesList = new ArrayList<>();
        Group g = (Group) groupList.getSelectedValue();
        if (g == null) {
            table.setVisible(false);
            return;
        } else {
            table.setVisible(true);
        }

        Context c = new Context();
        c.put("list", classesList);
        c.put("sqlQuery", "select b.*  from  ABSENCE_TBL a " +
                " left join CLASSES_TBL b on  (a.class_id = b.classes_id and " +
                " a.group_id = \"" + g.getId() + "\")");

        try {
            CommandExecutor.getInstance().execute("loadSchedule", c, new Runnable() {
                @Override
                public void run() {
                    MyTableModel model = (MyTableModel) table.getModel();
                    for (Classes c : classesList) {
                        if (c != null) {
                            classes.set(c.getTime(), c);
                            int row = c.getTime();
                            if (c.getAudience() > 0) {
                                model.setValueAt(c.getAudience(), row, 3);
                            }
                            model.setValueAt(getSubjectById(c.getSubjectId()), row, 1);
                            model.setValueAt(getTeacherById(c.getTeacherId()), row, 2);
                        }
                    }
                    updateUI();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * повертає предмет за ключем
     * @param id ключ предмету
     * @return предмет
     */
    private Subject getSubjectById(UUID id) {

        for (Subject s : subjects) {
            if (s != null && s.getId().equals(id)) {
                return s;
            }
        }

        return emptySubject;
    }

    /**
     * повертає викладача за ключем
     * @param id ключ викладача
     * @return викладача
     */
    private Teacher getTeacherById(UUID id) {

        for (Teacher t : teachers) {
            if (t != null && t.getId().equals(id)) {
                return t;
            }
        }
        return emptyTeacher;
    }

    /**
     * регує на зміну факультету, завантажує групи ти розклад
     */
    private void facultyChangedAction() {
        final Faculty f = (Faculty) comboBoxFaculty.getSelectedItem();


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


        DefaultListModel<Group> model = new DefaultListModel<Group>();
        for (Group g : groups) {
            model.addElement(g);
        }
        groupList.setModel(model);

        if (groups.size() > 0) {
            groupList.setSelectedIndex(0);
        }
        updateUI();
    }

    /**
     * оновлює інтерфейс
     */
    private void updateUI() {
        frame.validate();
        frame.repaint();
    }

    /**
     * перезавантажує таблицю розкладу
     */
    private void reloadTable() {

        classes = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            classes.add(null);
        }

        table.setModel(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 900));
        table.setFillsViewportHeight(true);

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                tableValueChangedAction(e);
            }
        });

        createScheduleTable();

        initColumnSizes(table);
        setUpSubjectColumn(table, table.getColumnModel().getColumn(1));
        setUpTeacherColumn(table, table.getColumnModel().getColumn(2));

        fillTable();
    }

    /**
     * перезавантажує всі компоненти
     */
    private void reloadAll() {

        reloadTable();

        java.util.List<Faculty> faculties = null;


        try {
            faculties = AppConst.DAO.select(Faculty.class, "");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        comboBoxFaculty.setModel(new DefaultComboBoxModel(faculties.toArray()));

        comboBoxFaculty.setSelectedIndex(0);
    }


    /**
     * створює таблицю розкладу
     */
    private void createScheduleTable() {

        data = new ArrayList<>(30);

        for (int i = 0; i < 6; i++) {

            data.add(new Object[]{daysOfWeek[i], "", "", ""});

            for (int j = 1; j < 6; j++) {
                data.add(new Object[]{new Integer(j), "", "", (Integer) null});
            }
        }

    }

    /**
     * ініціалізує розміри колонок таблиці
     * @param table таблиця розкладу
     */
    private void initColumnSizes(JTable table) {
        MyTableModel model = (MyTableModel) table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
                table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 4; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                    null, column.getHeaderValue(),
                    false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                    getTableCellRendererComponent(
                            table, longValues[i],
                            false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            if (DEBUG) {
                System.out.println("Initializing width of column "
                        + i + ". "
                        + "headerWidth = " + headerWidth
                        + "; cellWidth = " + cellWidth);
            }

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

    /**
     * ініціалізує колонку предметів
     * @param table таблиця розкладу
     * @param subjectColumn колонка розкладу
     */
    public void setUpSubjectColumn(JTable table,
                                   TableColumn subjectColumn) {


        try {
            subjects = AppConst.DAO.select(Subject.class, "");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        comboBoxSubject = new JComboBox();

        comboBoxSubject.addItem(emptySubject);

        for (Subject s : subjects) {
            comboBoxSubject.addItem(s);
            System.out.println(":  : " + s.toString());
        }

        subjectColumn.setCellEditor(new DefaultCellEditor(comboBoxSubject));
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        subjectColumn.setCellRenderer(renderer);
    }

    /**
     * ініціалізує колонку викладачів
     * @param table таблиця розкладу
     * @param teacherColumn колонка розкладу
     */
    public void setUpTeacherColumn(JTable table,
                                   TableColumn teacherColumn) {


        try {
            teachers = AppConst.DAO.select(Teacher.class, "");
            for (Teacher t : teachers) {
                t.setProfile((Profile) AppConst.DAO.read(Profile.class, t.getProfileId()));
                t.setWorkerInfo((WorkerInfo) AppConst.DAO.read(WorkerInfo.class, t.getWorkerInfoId()));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        comboBoxTeacher = new JComboBox();

        comboBoxTeacher.addItem(emptyTeacher);

        for (Teacher s : teachers) {
            comboBoxTeacher.addItem(s);
            System.out.println(":  : " + s.toString());
        }
        teacherColumn.setCellEditor(new DefaultCellEditor(comboBoxTeacher));
       DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        teacherColumn.setCellRenderer(renderer);
    }


    /**
     * модель для таблиці
     */
    class MyTableModel extends AbstractTableModel {

        public final Object[] longValues = {"Sharon", "Campione",
                "None of the above",
                new Integer(20), Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 1 || row % 6 == 0) {
                return false;
            }
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data2:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data.get(i)[j]);
                }
                System.out.println();
            }
        }
    }

}
