package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.User;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;
import com.fiot.ui.Utils;

import javax.swing.*;
import java.util.UUID;

/**
 * команда виконує встановлення зв’язку друзі між 2 профілями
 */
@COMMAND(key = "registerUser")
@CONTEXT(list = {
        @PARAMETER(key = "firstName", type = String.class),
        @PARAMETER(key = "secondName", type = String.class),
        @PARAMETER(key = "email", type = String.class),
        @PARAMETER(key = "password", type = String.class)
})
public class RegisterUserCommand extends AbstractCommand {

    public RegisterUserCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public RegisterUserCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        String firstName = (String) context.get("firstName");
        String secondName = (String) context.get("secondName");
        String email = (String) context.get("email");
        String password = (String) context.get("password");

        try {

            User user = new User(
                    UUID.randomUUID(),
                    firstName,
                    secondName,
                    email,
                    password,
                    "user"
            );

            AppConst.DAO.insert(user);

        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
