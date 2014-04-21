package com.oyster.amo.lab05;

import javax.swing.*;

/**
 * @author bamboo
 * @since 4/16/14 11:22 PM
 */
public class Gaus {

    public static void main(String[] args) {

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

        GausFrame frame = new GausFrame("Розв’язування систем лінійних рівнянь");
    }

}
