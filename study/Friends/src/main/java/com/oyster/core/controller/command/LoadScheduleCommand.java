package com.oyster.core.controller.command;

import com.oyster.app.AppConst;
import com.oyster.app.model.Classes;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.dao.exception.DAOException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * команда виконує завантаження розкладу, виконуюючи SQL-запит, що їй передається
 */
@COMMAND(key = "loadSchedule")
@CONTEXT(list = {
        @PARAMETER(key = "sqlQuery", type = String.class),
        @PARAMETER(key = "list", type = ArrayList.class)
})
public class LoadScheduleCommand extends AbstractCommand {

    public LoadScheduleCommand() {
    }

    /**
     * Конструктор
     * @param context1 контекст команди
     */
    public LoadScheduleCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        String sqlQuery = (String) context.get("sqlQuery");

        List<Classes> classesList = (List<Classes>) context.get("list");

        try {
            List<Classes> list = AppConst.DAO.select(Classes.class, sqlQuery);
            for (Classes c : list) {
                classesList.add(c);
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }


        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
