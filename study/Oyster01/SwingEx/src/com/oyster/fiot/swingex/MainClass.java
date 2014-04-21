package com.oyster.fiot.swingex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author bamboo
 * @since 4/13/14 12:28 PM
 */
public class MainClass extends JFrame {

    public static void main(String[] args) {


        new MainClass();
    }

    public MainClass() {

        setTitle("Hello");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(600, 400));

        addComponent(getContentPane());

        pack();

        setVisible(true);


    }

    private void addComponent(Container contentPane) {

        final JTextField text = new JTextField("no text");
        final JTextField textToEnter = new JTextField("ttt");


        JPanel p = new JPanel();
        p.setBackground(Color.ORANGE);
        p.setPreferredSize(new Dimension(100, 100));

        contentPane.setLayout(new BorderLayout());

        contentPane.add(p, BorderLayout.NORTH);

        final ButtonAction action = new ButtonAction("Click me !");


        JButton b = new JButton(action);

        contentPane.add(b, BorderLayout.WEST);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText(textToEnter.getText());
                System.out.println("Clicked");
                textToEnter.setBackground(Color.YELLOW);



            }
        });

        JList<String> list = new JList<String>();

        DefaultListModel<String> m = new DefaultListModel<>();
        for(int i = 0; i < 100; i++) {
            m.addElement(i + "\t: " +  "this is fucking text !!!");
        }

        list.setModel(m);
        list.setAutoscrolls(true);

        contentPane.add(list, BorderLayout.EAST);

        textToEnter.addPropertyChangeListener("background", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(String.format("old : %s\tnew : %s\n", evt.getOldValue(), evt.getNewValue()));
                text.setText(evt.getNewValue().toString());
//                action.setEnabled(false);

            }
        });



        contentPane.add(b, BorderLayout.WEST);
        contentPane.add(text, BorderLayout.SOUTH);
        contentPane.add(textToEnter, BorderLayout.CENTER);

        contentPane.add(new JButton(action), BorderLayout.NORTH);

    }


}
