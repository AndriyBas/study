package com.oyster.ui;

import com.oyster.core.controller.command.Context;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/* 1.4 example used by DialogDemo.java. */
class NewStudentCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String userName = null;
    private String userSurmane = null;
    private String userBirthDate = null;
    private String studentFaculty;
    private String studentCourse;
    private String studentGroup;
    private String studentNZK;
    private String userPassword;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
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
    public NewStudentCustomDialog(Frame aFrame, MainForm parent) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);
        dd = parent;

        setTitle("Створити профіль студента");

        textField1 = new JTextField(10);
        textField2 = new JTextField(15);
        textField3 = new JTextField(15);
        textField4 = new JTextField(15);
        textField5 = new JTextField(15);
        textField6 = new JTextField(15);
        textField7 = new JTextField(15);
        mJPasswordField = new JPasswordField(15);

        //Create an array of the text and components to be displayed.
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


        setPreferredSize(new Dimension(350, 490));
        setMinimumSize(new Dimension(350, 490));
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
                userBirthDate = textField3.getText();
                studentFaculty = textField4.getText();
                studentCourse = textField5.getText();
                studentGroup = textField6.getText();
                studentNZK = textField7.getText();
                userPassword = new String(mJPasswordField.getPassword());

                boolean errorOccured = false;
                JTextComponent focusComponent = textField1;


                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (userName.trim().length() == 0) {
                    errorMsg.append(" ім’я студента");
                    errorOccured = true;
                }

                if (userSurmane.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  прізвище студента");
                    focusComponent = textField2;
                }
                if (userBirthDate.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  дату народження");
                    focusComponent = textField3;
                }

                if (studentFaculty.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  факультет");
                    focusComponent = textField4;
                }

                if (studentCourse.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  курс");
                    focusComponent = textField5;
                }
                if (studentGroup.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  групу");
                    focusComponent = textField6;
                }
                if (studentNZK.trim().length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  номер залікової книжки");
                    focusComponent = textField7;
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

                    // collect all data
                    Context c = new Context();
                    c.put("name", userName);
                    c.put("surname", userSurmane);
                    c.put("birthday", userBirthDate);
                    c.put("faculty", studentFaculty);
                    c.put("course", Integer.parseInt(studentCourse));
                    c.put("group", studentGroup);
                    c.put("bookNum", Integer.parseInt(studentNZK));
                    c.put("password", userPassword);

                    // and kick off action for performing
                    dd.performAction("registerStudent", c);

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
                    userName = null;
                    userSurmane = null;
                    userBirthDate = null;
                    studentFaculty = null;
                    studentCourse = null;
                    studentGroup = null;
                    studentNZK = null;
                    userPassword = null;
                    focusComponent.requestFocusInWindow();
                }
            } else { //user closed dialog or clicked cancel
                userName = null;
                userSurmane = null;
                userBirthDate = null;
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
     * This method clears the dialog and hides it.
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