package com.oyster.gabka;

import javax.swing.*;

/**
 * Created by bamboo on 29.04.14.
 */
public class GabkaMain {

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

        GabkaForm frame = new GabkaForm("Crazy Frog");


    }

}
