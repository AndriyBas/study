package com.fiot.ui;

import javax.swing.*;

/**
 * утилітний клас для показу стандартних повідомлень
 */
public class Utils {

    /**
     * вставляє переноси у стрічку, щоб текст поміщався на екрані
     *
     * @param s текст для опрацювання
     * @return опрацьований текст
     */
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
    }

    /**
     * показує діалог із повідомленням про помилку
     *
     * @param parent   фікно, що викликає діалог
     * @param errorMsg повідомлення для показу
     */
    public static void showErrorDialog(JFrame parent, String errorMsg) {

        JOptionPane.showMessageDialog(
                parent,
                errorMsg,
                "Спробуйте ще раз",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
