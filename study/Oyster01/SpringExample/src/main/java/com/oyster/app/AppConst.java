package com.oyster.app;

import com.oyster.app.model.Admin;
import com.oyster.app.model.Profile;
import org.springframework.context.ApplicationContext;

/**
 * Created by bamboo on 07.05.14.
 */
public class AppConst {

    private static Admin currentAdmin;
    private static Profile currentAdminProfile;

    public static ApplicationContext context;


    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        AppConst.currentAdmin = currentAdmin;
    }

    public static Profile getCurrentAdminProfile() {
        return currentAdminProfile;
    }

    public static void setCurrentAdminProfile(Profile currentAdminProfile) {
        AppConst.currentAdminProfile = currentAdminProfile;
    }
}
