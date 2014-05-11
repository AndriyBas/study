package com.oyster.app;

import com.oyster.app.model.Admin;
import com.oyster.config.AppConfig;
import com.oyster.dao.impl.DAOCRUDJdbc;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Created by bamboo on 07.05.14.
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


    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        AppConst.currentAdmin = currentAdmin;
    }


}
