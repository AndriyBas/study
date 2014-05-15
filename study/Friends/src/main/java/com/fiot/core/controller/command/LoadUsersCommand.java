package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.User;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * команда виконує завантаження користувачів виконуюючи SQL-запит, що їй передається
 */

@COMMAND(key = "loadUsers")
@CONTEXT(list = {
        @PARAMETER(key = "sqlQuery", type = String.class),
        @PARAMETER(key = "list", type = ArrayList.class)
})
public class LoadUsersCommand extends AbstractCommand {

    public LoadUsersCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public LoadUsersCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        String sqlQuery = (String) context.get("sqlQuery");

        List<User> users = (List<User>) context.get("list");

        try {
            List<User> list = AppConst.DAO.select(User.class, sqlQuery);
            for (User u : list) {
                users.add(u);
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
