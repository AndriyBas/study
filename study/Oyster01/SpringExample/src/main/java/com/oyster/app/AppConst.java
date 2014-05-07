package com.oyster.app;

import com.oyster.app.model.Admin;
import com.oyster.app.model.Profile;
import com.oyster.app.model.WorkerInfo;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Created by bamboo on 07.05.14.
 */
public class AppConst {

    private static Admin currentAdmin;

    public static ApplicationContext context;

    public static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("d/M/y");
    }


    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        AppConst.currentAdmin = currentAdmin;
    }
}
