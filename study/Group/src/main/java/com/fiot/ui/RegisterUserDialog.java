package com.fiot.ui;

import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Клас відповідає за діалог, який збирає усі дані необхідні для створення нового студента
 */
public class RegisterUserDialog extends JDialog
        implements PropertyChangeListener {
    private String firstName = null;
    private String secondName = null;


    private String userEmail;

    private String userPassword;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JPasswordField mJPasswordField;
    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    final GroupFrame mGroupFrame;

    /**
     * Створює нове ділогове вікно
     *
     * @param aFrame вкно, що викликало діалог
     */
    public RegisterUserDialog(GroupFrame aFrame) {
        super(aFrame, true);
        mGroupFrame = aFrame;
        super.setLocationRelativeTo(null);

        setTitle("Додати новий акаунт");

        textField1 = new JTextField(10);
        textField2 = new JTextField(15);
        textField4 = new JTextField(15);
        mJPasswordField = new JPasswordField(15);

        String msgString1 = "Ім’я : ";
        String msgString2 = "Прізвище: ";
        String msgString4 = "Пошта : ";
        String msgString8 = "Пароль : ";
        Object[] array = {msgString1, textField1,
                msgString2, textField2,
                msgString4, textField4,
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


        setPreferredSize(new Dimension(350, 280));
        setMinimumSize(new Dimension(350, 280));
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
                userEmail = textField4.getText().trim();
                userPassword = new String(mJPasswordField.getPassword()).trim();

                boolean errorOccured = false;
                JTextComponent focusComponent = textField1;

                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (firstName.length() == 0) {
                    errorMsg.append(" ім’я ");
                    errorOccured = true;
                }

                if (secondName.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  прізвище ");
                    focusComponent = textField2;
                }


                if (userEmail.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  пошту ");
                    focusComponent = textField4;
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


                    // collect all data
                    Context c = new Context();
                    c.put("firstName", firstName);
                    c.put("secondName", secondName);
                    c.put("email", userEmail);
                    c.put("password", userPassword);

                    try {
                        CommandExecutor.getInstance().execute("registerUser", c, new Runnable() {
                            @Override
                            public void run() {
                                mGroupFrame.reloadUsers();
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    clearAndHide();
                } else {
                    //text was invalid
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            RegisterUserDialog.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    firstName = null;
                    secondName = null;
                    userEmail = null;
                    userPassword = null;
                    focusComponent.requestFocusInWindow();
                }
            } else {
                firstName = null;
                secondName = null;
                userEmail = null;
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
        textField4.setText(null);
        mJPasswordField.setText(null);
        setVisible(false);
        dispose();
    }
}