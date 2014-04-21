package com.oyster.fiot;

import javax.swing.*;

/**
 * @author bamboo
 * @since 4/16/14 8:25 PM
 */
public class HalfDivider {


    public static void main(final String[] args) {


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        HalfDividerFrame demo = new HalfDividerFrame("Метод половинного ділення");
    }

}
