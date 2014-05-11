package com.oyster.ui.dialogs;

import com.oyster.app.AppConst;
import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Клас відповідає за діалог, який збирає усі дані необхідні для створення нового студента
 */
public class NewStudentCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String firstName = null;
    private String secondName = null;

    private String userBirthDateStr = null;
    private Date userBirthDate = null;

    private String studentFaculty;

    private String studentCourse;
    private Integer studentCourseInt;

    private String studentGroup;

    private String studentNZK;
    private Integer studentNZKInt;

    private String userPassword;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JPasswordField mJPasswordField;
    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    /**
     * Створює нове ділогове вікно
     *
     * @param aFrame вкно, що викликало діалог
     */
    public NewStudentCustomDialog(Frame aFrame) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);

        setTitle("Створити профіль студента");

        textField1 = new JTextField(10);
        textField2 = new JTextField(15);
        textField3 = new JFormattedTextField(AppConst.DATE_FORMAT);
        textField4 = new JTextField(15);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter courseFormatter = new NumberFormatter(format);
        courseFormatter.setValueClass(Integer.class);
        courseFormatter.setMinimum(1);
        courseFormatter.setMaximum(6);
        courseFormatter.setCommitsOnValidEdit(true);
        textField5 = new JFormattedTextField(courseFormatter);

        textField6 = new JTextField(15);

        NumberFormatter NZKFormater = new NumberFormatter(format);
        NZKFormater.setValueClass(Integer.class);
        NZKFormater.setMinimum(1);
        NZKFormater.setMaximum(Integer.MAX_VALUE);
        NZKFormater.setCommitsOnValidEdit(true);
        textField7 = new JFormattedTextField(NZKFormater);

        mJPasswordField = new JPasswordField(15);
        String msgString1 = "Ім’я студента : ";
        String msgString2 = "Прізвище студента : ";
        String msgString3 = "День народження : ";
        String msgString4 = "Факультет : ";
        String msgString5 = "Курс : ";
        String msgString6 = "Група : ";
        String msgString7 = "Номер залікової книжки : ";
        String msgString8 = "Пароль : ";
        Object[] array = {msgString1, textField1,
                msgString2, textField2,
                msgString3, textField3,
                msgString4, textField4,
                msgString5, textField5,
                msgString6, textField6,
                msgString7, textField7,
                msgString8, mJPasswordField};
        Object[] options = {btnString1, btnString2};

        optionPane = new JOptionPane(array,
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);
        setContentPane(optionPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(new Integer(
                        JOptionPane.CLOSED_OPTION));
            }
        });
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textField1.requestFocusInWindow();
            }
        });
        optionPane.addPropertyChangeListener(this);


        setPreferredSize(new Dimension(350, 490));
        setMinimumSize(new Dimension(350, 490));
    }

    /**
     * Метод спрацьовує на зміну властивостей у JOptionsPane
     *
     * @param e дані про подію
     */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                return;
            }
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {
                firstName = textField1.getText().trim();
                secondName = textField2.getText().trim();
                userBirthDateStr = textField3.getText().trim();
                studentFaculty = textField4.getText().trim();
                studentCourse = textField5.getText().trim();
                studentGroup = textField6.getText().trim();
                studentNZK = textField7.getText().trim();
                userPassword = new String(mJPasswordField.getPassword()).trim();

                boolean errorOccured = false;
                JTextComponent focusComponent = textField1;


                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (firstName.length() == 0) {
                    errorMsg.append(" ім’я студента");
                    errorOccured = true;
                }

                if (secondName.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  прізвище студента");
                    focusComponent = textField2;
                }
                if (userBirthDateStr.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  дату народження  у фрматі день/місяць/рік");
                    focusComponent = textField3;
                }

                if (studentFaculty.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  факультет");
                    focusComponent = textField4;
                }

                if (studentCourse.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  курс");
                    focusComponent = textField5;
                }
                if (studentGroup.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  групу");
                    focusComponent = textField6;
                }
                if (studentNZK.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  номер залікової книжки");
                    focusComponent = textField7;
                }

                if (userPassword.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  пароль");
                    focusComponent = mJPasswordField;
                }

                errorMsg.append("!");

                if (!errorOccured) {

                    try {
                        userBirthDate = (Date) ((JFormattedTextField) textField3)
                                .getFormatter().stringToValue(userBirthDateStr);
                        studentCourseInt = (Integer) ((JFormattedTextField) textField5)
                                .getFormatter().stringToValue(studentCourse);
                        studentNZKInt = (Integer) ((JFormattedTextField) textField7)
                                .getFormatter().stringToValue(studentNZK);

                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    // collect all data
                    Context c = new Context();
                    c.put("name", firstName);
                    c.put("surname", secondName);
                    c.put("birthday", userBirthDate.getTime());
                    c.put("faculty", studentFaculty);
                    c.put("course", studentCourseInt);
                    c.put("group", studentGroup);
                    c.put("bookNum", studentNZKInt);
                    c.put("password", userPassword);

                    try {
                        CommandExecutor.getInstance().execute("registerStudent", c, null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    clearAndHide();
                } else {
                    //text was invalid
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            NewStudentCustomDialog.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    firstName = null;
                    secondName = null;
                    userBirthDateStr = null;
                    studentFaculty = null;
                    studentCourse = null;
                    studentGroup = null;
                    studentNZK = null;
                    userPassword = null;
                    focusComponent.requestFocusInWindow();
                }
            } else {
                firstName = null;
                secondName = null;
                userBirthDateStr = null;
                studentFaculty = null;
                studentCourse = null;
                studentGroup = null;
                studentNZK = null;
                userPassword = null;
                clearAndHide();
            }
        }
    }


    /**
     * Метод очищує всі поля діалогу
     */
    public void clearAndHide() {
        textField1.setText(null);
        textField2.setText(null);
        textField3.setText(null);
        textField4.setText(null);
        textField5.setText(null);
        textField6.setText(null);
        textField7.setText(null);
        mJPasswordField.setText(null);
        setVisible(false);
        dispose();
    }
}