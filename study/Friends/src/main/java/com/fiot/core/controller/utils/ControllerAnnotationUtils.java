package com.fiot.core.controller.utils;

import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;

/**
 * Допоміжний клас для роботи із аннотаціями команди
 */
public class ControllerAnnotationUtils {

    /**
     * повертає ключ команди
     *
     * @param c Клас команди
     * @return ключ команди
     */
    public static String getCommandKey(Class c) {
        COMMAND t = (COMMAND) c.getAnnotation(COMMAND.class);
        return (t != null) ? t.key() : null;
    }

    /**
     * повертає список усіх параметрів команди
     *
     * @param c Клас команди
     * @return список усіх параметрів команди
     */
    public static PARAMETER[] getParameterList(Class c) {
        CONTEXT t = (CONTEXT) c.getAnnotation(CONTEXT.class);
        if (t == null) return null;
        return t.list();
    }

    /**
     * визначає, чи є даний параметр обов’язковим
     *
     * @param paramKey ключ параметра
     * @param command  команда, у якій перевіряємо
     * @return true - якщо необов’язковий, інакше - false
     */
    public static boolean paramIsOptional(String paramKey, Class command) {

        PARAMETER[] pl = getParameterList(command);
        for (PARAMETER p : pl) {
            if (p.key().equals(paramKey)) return p.optional();
        }

        return true;
    }
}
