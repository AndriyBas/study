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
 * команда виконує авторизацію користувача із логіном та паролем, що ії передаються
 */
@COMMAND(key = "changeStatus")
@CONTEXT(list = {
        @PARAMETER(key = "userId", type = UUID.class),
        @PARAMETER(key = "status", type = String.class)
})
public class ChangeStatusCommand extends AbstractCommand {

    public ChangeStatusCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public ChangeStatusCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        final UUID userID = (UUID) context.get("userId");
        final String status = (String) context.get("status");

        try {
            User user = AppConst.DAO.read(User.class, userID);
            user.setStatus(status);
            AppConst.DAO.update(user);
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

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
