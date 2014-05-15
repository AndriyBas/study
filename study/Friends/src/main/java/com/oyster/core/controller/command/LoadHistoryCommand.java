package com.oyster.core.controller.command;

import com.oyster.app.AppConst;
import com.oyster.app.model.History;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.dao.exception.DAOException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * команда виконує завантаження історії, виконуюючи SQL-запит, що їй передається
 */

@COMMAND(key = "loadHistory")
@CONTEXT(list = {
        @PARAMETER(key = "sqlQuery", type = String.class),
        @PARAMETER(key = "list", type = ArrayList.class)
})
public class LoadHistoryCommand extends AbstractCommand {

    public LoadHistoryCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public LoadHistoryCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        String sqlQuery = (String) context.get("sqlQuery");

        List<History> histories = (List<History>) context.get("list");

        try {
            List<History> list = AppConst.DAO.select(History.class, sqlQuery);
            for (History h : list) {
                histories.add(h);
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }


        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
