package com.oyster.core.controller.command;

import com.oyster.app.AppConst;
import com.oyster.app.model.Admin;
import com.oyster.app.model.Profile;
import com.oyster.app.model.WorkerInfo;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.ui.MainForm;
import com.oyster.ui.Utils;

import javax.swing.*;
import java.util.List;

/**
 * команда виконує авторизацію користувача із логіном та паролем, що ії передаються
 */
@COMMAND(key = "logIn")
@CONTEXT(list = {
        @PARAMETER(key = "username", type = String.class),
        @PARAMETER(key = "password", type = String.class)
})
public class LogInCommand extends AsyncCommand<String, Admin> {

    @Override
    protected Admin doInBackGround(Context context) {


        final String userName = (String) context.get("username");
        final String userPassword = (String) context.get("password");

        List<Profile> profiles = null;
        try {
            profiles = AppConst.DAO.select(Profile.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Profile p = (Profile) entity;
                    return userName.equals(p.getFirstName() + "-" + p.getSecondName())
                            && userPassword.equals(p.getPassword());
                }
            });
        } catch (final DAOException e) {
            e.printStackTrace();
            publishProgress(e.getMessage());
            return null;
        }

        if (profiles.size() == 0) {
            publishProgress("Помилка авторизації : неправильні логін чи пароль !");
            return null;
        }

        final Profile profile = profiles.get(0);
        WorkerInfo wi = null;
        List<Admin> admins = null;

        try {
            admins = AppConst.DAO.select(Admin.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Admin a = (Admin) entity;
                    return a.getProfileId().equals(profile.getId());
                }
            });
            wi = AppConst.DAO.read(WorkerInfo.class, admins.get(0).getWorkerInfoId());
        } catch (final Exception e) {
            e.printStackTrace();
            publishProgress(e.getMessage());
            return null;
        }

        if (admins.size() == 0) {
            publishProgress("Помилка авторизації : дані логін та пароль не відповідають адміністратору !");
            return null;
        }

        Admin admin = admins.get(0);
        admin.setProfile(profile);
        admin.setWorkerInfo(wi);

        return admin;
    }

    @Override
    protected void onPostExecute(Admin admin) {
        if (admin != null) {
            AppConst.setCurrentAdmin(admin);
            new MainForm();
        } else {

        }
    }

    @Override
    protected void onProgressUpdate(final String... progress) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Utils.showErrorDialog(null, Utils.makePretty(progress[0]));
            }
        });
    }
}
