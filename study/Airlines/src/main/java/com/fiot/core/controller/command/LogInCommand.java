package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.User;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;
import com.fiot.ui.MainForm;
import com.fiot.ui.Utils;

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

        final String userName = (String) context.get("username");
        final String userPassword = (String) context.get("password");

        List<User> profiles = null;
        try {
            profiles = AppConst.DAO.select(User.class, "select * from USER_TBL where email =\"" +
                    userName + "\" AND password = \"" + userPassword + "\";");
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

        final User user = profiles.get(0);

        AppConst.setCurrentUser(user);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm form = new MainForm();
            }
        });

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
