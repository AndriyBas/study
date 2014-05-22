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
 * команда видаляє користувача із бази
 */
@COMMAND(key = "removeUser")
@CONTEXT(list = {
        @PARAMETER(key = "userId", type = UUID.class)
})
public class RemoveUserCommand extends AbstractCommand {

    public RemoveUserCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public RemoveUserCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        UUID userID = (UUID) context.get("userId");

        try {
            User user = new User();
            user.setId(userID);
            AppConst.DAO.delete(user);
        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
