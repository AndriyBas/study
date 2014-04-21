package com.oyster.fiot.swingex;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author bamboo
 * @since 4/13/14 2:13 PM
 */
public class ButtonAction extends AbstractAction {

    public ButtonAction(String s) {
        super(s);
    }

    public ButtonAction(String name, Icon i) {
        super(name, i);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(getClass().getSimpleName() + " : actionPerformed");
    }
}
