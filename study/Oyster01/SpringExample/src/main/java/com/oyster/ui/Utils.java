package com.oyster.ui;

import javax.swing.*;

/**
 * Created by bamboo on 07.05.14.
 */
public class Utils {

    public static String makePretty(String s) {
        String[] words = s.split("\\s+");
        int lineWidth = 0;
        StringBuilder msg = new StringBuilder(s.length());
        for (String w : words) {
            msg.append(w);
            msg.append(" ");
            lineWidth += w.length();
            if (lineWidth > 50) {
                lineWidth = 0;
                msg.append("\n");
            }
        }

        return msg.toString();
//        return toUTF8(msg.toString());
    }

    public static void showErrorDialog(JFrame parent, String errorMsg) {

        JOptionPane.showMessageDialog(
                parent,
                errorMsg,
                "Спробуйте ще раз",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
