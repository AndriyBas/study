package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.Message;
import com.fiot.app.model.User;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * команда виконує завантаження постів
 */

@COMMAND(key = "loadPosts")
@CONTEXT(list = {
        @PARAMETER(key = "sqlQuery", type = String.class),
        @PARAMETER(key = "list", type = ArrayList.class)
})
public class LoadPostsCommand extends AbstractCommand {

    public LoadPostsCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public LoadPostsCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        String sqlQuery = (String) context.get("sqlQuery");

        List<Message> messages = (List<Message>) context.get("list");

        try {
            List<Message> list = AppConst.DAO.select(Message.class, sqlQuery);
            for (Message m : list) {
                m.setAuthor((User) AppConst.DAO.read(User.class, m.getAuthotID()));
                messages.add(m);

            }

        } catch (DAOException e) {
            e.printStackTrace();
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
