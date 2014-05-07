package com.oyster.ui.dialogs;

import com.oyster.core.controller.command.Context;
import com.oyster.ui.MainForm;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 1.4 example used by DialogDemo.java. */
public class NewTeacherCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String userName = null;
    private String userSurmane = null;

    private String userBirthDateStr = null;
    private Date userBirthDate = null;

    private String teacherPosition;

    private String teacherSalaryStr;
    private Integer teacherSalaryInt;

    private String teacherDateHiredStr;
    private Date teacherDateHired;

    private String userPassword;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JPasswordField mJPasswordField;
    private MainForm dd;

    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return userName;
    }

    /**
     * Creates the reusable dialog.
     */
    public NewTeacherCustomDialog(Frame aFrame, MainForm parent) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);
        dd = parent;

        setTitle("Створити профіль викладача");

        DateFormat dateFormat = new SimpleDateFormat("d/M/y");


        textField1 = new JTextField(10);
        textField2 = new JTextField(15);
        textField3 = new JFormattedTextField(dateFormat);
        textField4 = new JTextField(15);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        textField5 = new JFormattedTextField(formatter);

        textField6 = new JFormattedTextField(dateFormat);
        mJPasswordField = new JPasswordField(15);
        mJPasswordField = new JPasswordField(15);

        //Create an array of the text and components to be displayed.
        String msgString1 = "Ім’я викладача: ";
        String msgString2 = "Прізвище викладача : ";
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

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                optionPane.setValue(new Integer(
                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textField1.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
//        textField1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                optionPane.setValue(btnString1);
//            }
//        });

//        textField2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                optionPane.setValue(btnString1);
//            }
//        });


        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(350, 450));
        setMinimumSize(new Dimension(350, 450));

    }

    private ActionListener textFieldActionLstener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            optionPane.setValue(btnString1);
        }
    };

    /**
     * This method reacts to state changes in the option pane.
     */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {
                userName = textField1.getText();
                userSurmane = textField2.getText();
                userBirthDateStr = textField3.getText();
                teacherPosition = textField4.getText();
                teacherSalaryStr = textField5.getText();
                teacherDateHiredStr = textField6.getText();
                userPassword = new String(mJPasswordField.getPassword());

                boolean errorOccured = false;
                JTextComponent focusComponent = textField1;


                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (userName.trim().length() == 0) {
                    errorMsg.append(" ім’я викладача");
                    errorOccured = true;
                }

                if (userSurmane.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  прізвище викладача");
                    focusComponent = textField2;
                }
                if (userBirthDateStr.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  дату народження у фрматі день/місяць/рік");
                    focusComponent = textField3;
                }

                if (teacherPosition.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  посаду");
                    focusComponent = textField4;
                }

                if (teacherSalaryStr.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  зарплату");
                    focusComponent = textField5;
                }
                if (teacherDateHiredStr.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  дату прийняття у фрматі день/місяць/рік");
                    focusComponent = textField6;
                }


                if (userPassword.trim().length() == 0) {
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

                        teacherSalaryInt = (Integer) ((JFormattedTextField) textField5)
                                .getFormatter().stringToValue(teacherSalaryStr);

                        teacherDateHired = (Date) ((JFormattedTextField) textField6)
                                .getFormatter().stringToValue(teacherDateHiredStr);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    // collect all data
                    Context c = new Context();
                    c.put("name", userName);
                    c.put("surname", userSurmane);
                    c.put("birthday", userBirthDate.getTime());
                    c.put("position", teacherPosition);
                    c.put("salary", teacherSalaryInt);
                    c.put("dateHired", teacherDateHired.getTime());
                    c.put("password", userPassword);

                    // and kick off action for performing
                    dd.performAction("registerTeacher", c);

                    clearAndHide();
                } else {
                    //text was invalid
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            NewTeacherCustomDialog.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    userName = null;
                    userSurmane = null;
                    userBirthDateStr = null;
                    teacherPosition = null;
                    teacherSalaryStr = null;
                    teacherDateHiredStr = null;
                    userPassword = null;
                    focusComponent.requestFocusInWindow();
                }
            } else { //user closed dialog or clicked cancel
                userName = null;
                userSurmane = null;
                userBirthDateStr = null;
                teacherPosition = null;
                teacherSalaryStr = null;
                teacherDateHiredStr = null;
                userPassword = null;
                clearAndHide();
            }
        }
    }

    /**
     * This method clears the dialog and hides it.
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