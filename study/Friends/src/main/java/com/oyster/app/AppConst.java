package com.oyster.app;

import com.oyster.app.model.Admin;
import com.oyster.config.AppConfig;
import com.oyster.dao.impl.DAOCRUDJdbc;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Клас містить константні поля(переважно статичні), що є спільними для
 * всього застосунку
 */
public class AppConst {

    private static Admin currentAdmin;

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
    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    /**
     * Встановлює адміністратора, що розпочав сесію
     *
     * @param currentAdmin адміністратор, що здійснив авторизацію
     */
    public static void setCurrentAdmin(Admin currentAdmin) {
        AppConst.currentAdmin = currentAdmin;
    }


}
