package com.oyster.ui.dialogs;

import com.oyster.core.controller.command.Context;
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
public class NewGroupCustomDialog extends JDialog
        implements PropertyChangeListener {
    private String groupName = null;
    private String groupFaculty = null;
    private JTextField textField1;
    private JTextField textField2;
    private MainForm dd;

    private JOptionPane optionPane;

    private String btnString1 = "Створити";
    private String btnString2 = "Відмінити";

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return groupName;
    }

    /**
     * Creates the reusable dialog.
     */
    public NewGroupCustomDialog(Frame aFrame, MainForm parent) {
        super(aFrame, true);
        super.setLocationRelativeTo(null);
        dd = parent;

        setTitle("Додати нову групу");

        textField1 = new JTextField(10);
        textField2 = new JTextField(15);

        //Create an array of the text and components to be displayed.
        String msgString1 = "Назва групи : ";
        String msgString2 = "Факультет : ";
        Object[] array = {msgString1, textField1, msgString2, textField2};

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

        setPreferredSize(new Dimension(350, 190));
        setMinimumSize(new Dimension(350, 150));
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

                    // collect all data
                    Context c = new Context();
                    c.put("name", groupName);
                    c.put("faculty", groupFaculty);
                    // and kick off action for performing
                    dd.performAction("registerGroup", c);

                    clearAndHide();
                } else {
                    //text was invalid
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
            } else { //user closed dialog or clicked cancel
                groupName = null;
                groupFaculty = null;
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
        setVisible(false);
        dispose();
    }
}