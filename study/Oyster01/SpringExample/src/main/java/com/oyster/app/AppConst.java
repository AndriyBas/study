package com.oyster.app;

import com.oyster.app.model.Admin;
import com.oyster.dao.impl.DAOCRUDJdbc;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Created by bamboo on 07.05.14.
 */
public class AppConst {

    private static Admin currentAdmin;

    public static ApplicationContext CONTEXT;

    public static SimpleDateFormat dateFormat;

    public static int SESSION_TIME;

    public static DAOCRUDJdbc DAO;

    static {
        dateFormat = new SimpleDateFormat("d/M/y");
        SESSION_TIME = 0;
    }


    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        AppConst.currentAdmin = currentAdmin;
    }
}
