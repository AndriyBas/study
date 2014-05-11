package com.oyster.ui.dialogs;

import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;

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
 * Клас відповідає за діалог, який збирає усі дані необхідні для створення нової групи
 */
public class NewGroupCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String groupName = null;
    private String groupFaculty = null;
    private JTextField textField1;
    private JTextField textField2;

    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";


    /**
     * Створює нове ділогове вікно
     *
     * @param aFrame вкно, що викликало діалог
     */
    public NewGroupCustomDialog(Frame aFrame) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);

        setTitle("Додати нову групу");

        textField1 = new JTextField(10);
        textField2 = new JTextField(15);

        String msgString1 = "Назва групи : ";
        String msgString2 = "Факультет : ";
        Object[] array = {msgString1, textField1, msgString2, textField2};

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

        setPreferredSize(new Dimension(350, 190));
        setMinimumSize(new Dimension(350, 150));
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
                groupName = textField1.getText().trim();
                groupFaculty = textField2.getText().trim();
                boolean errorOccured = false;
                JTextComponent focusComponent = textField1;

                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (groupName.length() == 0) {
                    errorMsg.append(" назву групи");
                    errorOccured = true;
                }
                if (groupFaculty.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(" та");
                    }
                    errorOccured = true;
                    errorMsg.append("  факльтет");
                    focusComponent = textField2;
                }
                errorMsg.append("!");

                if (!errorOccured) {
                    Context c = new Context();
                    c.put("name", groupName);
                    c.put("faculty", groupFaculty);
                    try {
                        CommandExecutor.getInstance().execute("registerGroup", c, null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    clearAndHide();
                } else {
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            NewGroupCustomDialog.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    groupName = null;
                    groupFaculty = null;
                    focusComponent.requestFocusInWindow();
                }
            } else {
                groupName = null;
                groupFaculty = null;
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
        setVisible(false);
        dispose();
    }
}