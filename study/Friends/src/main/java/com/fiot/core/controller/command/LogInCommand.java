package com.fiot.core.controller.command;

import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.ui.MainFrame;

import javax.swing.*;

/**
 * команда виконує авторизацію користувача із логіном та паролем, що ії передаються
 */
@COMMAND(key = "logIn")
@CONTEXT(list = {
        @PARAMETER(key = "username", type = String.class),
        @PARAMETER(key = "password", type = String.class)
})
public class LogInCommand extends AbstractCommand {

    public LogInCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public LogInCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        /*
        final String userName = (String) context.get("username");
        final String userPassword = (String) context.get("password");

        List<User> profiles = null;
        try {
            profiles = AppConst.DAO.select(User.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    User p = (User) entity;
                    return userName.equals(p.getFirstName() + "-" + p.getSecondName())
                            && userPassword.equals(p.getPassword());
                }
            });
        } catch (final DAOException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, e.getMessage());
                }
            });
            return;
        }

        if (profiles.size() == 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, "Помилка авторизації : неправильні логін чи пароль !");
                }
            });

            return;
        }

        final User profile = profiles.get(0);
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null,e.getMessage());
                }
            });

            return;
        }

        if (admins.size() == 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, "Помилка авторизації : дані логін та пароль не відповідають адміністратору !");
                }
            });

            return;
        }

        final Admin admin = admins.get(0);
        admin.setProfile(profile);
        admin.setWorkerInfo(wi);

        AppConst.setCurrentUser(admin);*/

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame form = new MainFrame();
            }
        });

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
