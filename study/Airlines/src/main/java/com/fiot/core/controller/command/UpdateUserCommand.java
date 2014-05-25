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
 * Created by bamboo on 25.05.14.
 */
@COMMAND(key = "updateUser")
@CONTEXT(list = {
        @PARAMETER(key = "id", type = UUID.class),
        @PARAMETER(key = "first_name", type = String.class),
        @PARAMETER(key = "second_name", type = String.class),
        @PARAMETER(key = "email", type = String.class),
        @PARAMETER(key = "password", type = String.class),
        @PARAMETER(key = "phone", type = String.class),
        @PARAMETER(key = "address", type = String.class)
})
public class UpdateUserCommand extends AbstractCommand {

    public UpdateUserCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public UpdateUserCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        User user = new User(
                (UUID) context.get("id"),
                (String) context.get("first_name"),
                (String) context.get("second_name"),
                (String) context.get("email"),
                (String) context.get("password"),
                (String) context.get("phone"),
                (String) context.get("address")
        );

        try {
            AppConst.DAO.replace(user);
        } catch (final DAOException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, ("Помилка оновлення даних : \n" + e.getMessage()));
                }
            });
            return;
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }
}