package com.fiot.app;

import com.fiot.app.model.User;
import com.fiot.config.AppConfig;
import com.fiot.dao.impl.DAOCRUDJdbc;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Клас містить константні поля(переважно статичні), що є спільними для
 * всього застосунку
 */
public class AppConst {

    private static User currentUser;

    public static ApplicationContext CONTEXT;

    public static SimpleDateFormat DATE_FORMAT;

    public static int SESSION_TIME;

    public static DAOCRUDJdbc DAO;

    public static AppConfig APP_CONFIG;

    static {
        DATE_FORMAT = new SimpleDateFormat("d/M/y");
        SESSION_TIME = 0;
    }


    /**
     * Повертає адміністратора, що останній раз авторизувався
     *
     * @return адміністратора, що залогінився, або null, якщо такого немає
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Встановлює адміністратора, що розпочав сесію
     *
     * @param currentUser адміністратор, що здійснив авторизацію
     */
    public static void setCurrentUser(User currentUser) {
        AppConst.currentUser = currentUser;
    }


}
