package com.fiot.ui;

import javax.swing.*;

/**
 * утилітний клас для показу стандартних повідомлень
 */
public class Utils {

    /**
     * показує діалог із повідомленням про помилку
     *
     * @param parent   фікно, що викликає діалог
     * @param errorMsg повідомлення для показу
     */
    public static void showErrorDialog(JFrame parent, String errorMsg) {

        // вставляє переноси у стрічку, щоб текст поміщався на екрані
        String[] words = errorMsg.split("\\s+");
        int lineWidth = 0;
        StringBuilder msg = new StringBuilder(errorMsg.length());
        for (String w : words) {
            msg.append(w);
            msg.append(" ");
            lineWidth += w.length();
            if (lineWidth > 50) {
                lineWidth = 0;
                msg.append("\n");
            }
        }

        JOptionPane.showMessageDialog(
                parent,
                msg.toString(),
                "Спробуйте ще раз",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
