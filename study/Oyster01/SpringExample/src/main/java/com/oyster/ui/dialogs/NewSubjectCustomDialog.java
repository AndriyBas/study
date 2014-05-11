package com.oyster.ui.dialogs;

import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;
import com.oyster.core.controller.exception.CommandNotFoundException;
import com.oyster.core.controller.exception.InvalidCommandParameterException;
import com.oyster.ui.MainForm;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/* 1.4 example used by DialogDemo.java. */
public class NewSubjectCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String subjectName = null;
    private JTextField textFieldSubjectName;

    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return subjectName;
    }

    /**
     * Creates the reusable dialog.
     */
    public NewSubjectCustomDialog(Frame aFrame) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);

        setTitle("Додати предмет");

        textFieldSubjectName = new JTextField(10);

        //Create an array of the text and components to be displayed.
        String msgString1 = "Назва предмету : ";
        Object[] array = {msgString1, textFieldSubjectName};

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
                textFieldSubjectName.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
//        textFieldSubjectName.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                optionPane.setValue(btnString1);
//            }
//        });

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);

        setPreferredSize(new Dimension(350, 140));
        setMinimumSize(new Dimension(350, 140));
    }

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
                    // and kick off action for performing
                    try {
                        CommandExecutor.getInstance().execute("registerSubject", c, null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    clearAndHide();
                } else {
                    //text was invalid
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
            } else { //user closed dialog or clicked cancel
                subjectName = null;
                clearAndHide();
            }
        }
    }

    /**
     * This method clears the dialog and hides it.
     */
    public void clearAndHide() {
        textFieldSubjectName.setText(null);
        setVisible(false);
        dispose();
    }
}