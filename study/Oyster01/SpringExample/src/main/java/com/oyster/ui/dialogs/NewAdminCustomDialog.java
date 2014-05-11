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
 * Клас відповідає за діалог, який збирає усі дані необхідні для створення нового адміністратора
 */
public class NewAdminCustomDialog extends JDialog
        implements PropertyChangeListener {

    private String userName = null;
    private String userSurmane = null;

    private String userBirthDateStr = null;
    private Date userBirthDate = null;

    private String adminPosition;

    private String adminSalary;
    private Integer adminSalaryInt;

    private String adminDateHiredStr;
    private Date adminDateHired;

    private String userPassword;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JPasswordField mJPasswordField;

    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    /**
     * Створює нове ділогове вікно
     *
     * @param aFrame вкно, що викликало діалог
     */
    public NewAdminCustomDialog(Frame aFrame) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);

        setTitle("Створити профіль адміністратора");


        textField1 = new JTextField(10);
        textField2 = new JTextField(15);
        textField3 = new JFormattedTextField(AppConst.DATE_FORMAT);
        textField4 = new JTextField(15);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setCommitsOnValidEdit(true);
        textField5 = new JFormattedTextField(formatter);

        textField6 = new JFormattedTextField(AppConst.DATE_FORMAT);
        mJPasswordField = new JPasswordField(15);

        String msgString1 = "Ім’я адміністратора: ";
        String msgString2 = "Прізвище адміністратора : ";
        String msgString3 = "День народження : ";
        String msgString4 = "Посада : ";
        String msgString5 = "Зарплата / рік : ";
        String msgString6 = "Дата прийняття на роботу : ";
        String msgString7 = "Пароль : ";

        Object[] array = {msgString1, textField1,
                msgString2, textField2,
                msgString3, textField3,
                msgString4, textField4,
                msgString5, textField5,
                msgString6, textField6,
                msgString7, mJPasswordField};

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
        setPreferredSize(new Dimension(350, 450));
        setMinimumSize(new Dimension(350, 450));
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
                userName = textField1.getText().trim();
                userSurmane = textField2.getText().trim();
                userBirthDateStr = textField3.getText().trim();
                adminPosition = textField4.getText().trim();
                adminSalary = textField5.getText().trim();
                adminDateHiredStr = textField6.getText().trim();
                userPassword = new String(mJPasswordField.getPassword()).trim();

                boolean errorOccured = false;
                JTextComponent focusComponent = textField1;


                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (userName.length() == 0) {
                    errorMsg.append(" ім’я адміністратора");
                    errorOccured = true;
                }

                if (userSurmane.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  прізвище адміністратора");
                    focusComponent = textField2;
                }
                if (userBirthDateStr.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  дату народження у фрматі день/місяць/рік");
                    focusComponent = textField3;
                }

                if (adminPosition.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  посаду");
                    focusComponent = textField4;
                }

                if (adminSalary.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  зарплату");
                    focusComponent = textField5;
                }
                if (adminDateHiredStr.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  дату прийняття у фрматі день/місяць/рік");
                    focusComponent = textField6;
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
                        adminSalaryInt = (Integer) ((JFormattedTextField) textField5)
                                .getFormatter().stringToValue(adminSalary);

                        userBirthDate = (Date) ((JFormattedTextField) textField3)
                                .getFormatter().stringToValue(userBirthDateStr);

                        adminDateHired = (Date) ((JFormattedTextField) textField6)
                                .getFormatter().stringToValue(adminDateHiredStr);

                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    Context c = new Context();
                    c.put("name", userName);
                    c.put("surname", userSurmane);
                    c.put("birthday", userBirthDate.getTime());
                    c.put("position", adminPosition);
                    c.put("salary", adminSalaryInt);
                    c.put("dateHired", adminDateHired.getTime());
                    c.put("password", userPassword);

                    try {
                        CommandExecutor.getInstance().execute("registerAdmin", c, null);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    clearAndHide();
                } else {
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            NewAdminCustomDialog.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    userName = null;
                    userSurmane = null;
                    userBirthDateStr = null;
                    adminPosition = null;
                    adminSalary = null;
                    adminDateHiredStr = null;
                    userPassword = null;
                    focusComponent.requestFocusInWindow();
                }
            } else {
                userName = null;
                userSurmane = null;
                userBirthDateStr = null;
                adminPosition = null;
                adminSalary = null;
                adminDateHiredStr = null;
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
        mJPasswordField.setText(null);
        setVisible(false);
        dispose();
    }
}