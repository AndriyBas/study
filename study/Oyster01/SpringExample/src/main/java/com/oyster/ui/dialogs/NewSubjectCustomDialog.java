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
 * Клас відповідає за діалог, який збирає усі дані необхідні для створення нового предмету
 */
public class NewSubjectCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String subjectName = null;
    private JTextField textFieldSubjectName;

    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    /**
     * Створює нове ділогове вікно
     *
     * @param aFrame вкно, що викликало діалог
     */
    public NewSubjectCustomDialog(Frame aFrame) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);

        setTitle("Додати предмет");
        textFieldSubjectName = new JTextField(10);
        String msgString1 = "Назва предмету : ";
        Object[] array = {msgString1, textFieldSubjectName};
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
                textFieldSubjectName.requestFocusInWindow();
            }
        });
        optionPane.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(350, 140));
        setMinimumSize(new Dimension(350, 140));
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
                subjectName = textFieldSubjectName.getText().trim();

                boolean errorOccured = false;
                JTextComponent focusComponent = textFieldSubjectName;


                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (subjectName.length() == 0) {
                    errorMsg.append(" назву предмету");
                    errorOccured = true;
                }

                errorMsg.append("!");

                if (!errorOccured) {
                    Context c = new Context();
                    c.put("name", subjectName);
                    try {
                        CommandExecutor.getInstance().execute("registerSubject", c);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    clearAndHide();
                } else {
                    focusComponent.selectAll();
                    JOptionPane.showMessageDialog(
                            NewSubjectCustomDialog.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                    subjectName = null;
                    focusComponent.requestFocusInWindow();
                }
            } else {
                subjectName = null;
                clearAndHide();
            }
        }
    }

    /**
     * Метод очищує всі поля діалогу
     */
    public void clearAndHide() {
        textFieldSubjectName.setText(null);
        setVisible(false);
        dispose();
    }
}