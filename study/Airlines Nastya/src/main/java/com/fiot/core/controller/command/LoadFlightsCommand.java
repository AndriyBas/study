package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.Flight;
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

@COMMAND(key = "loadFlights")
@CONTEXT(list = {
        @PARAMETER(key = "sqlQuery", type = String.class),
        @PARAMETER(key = "list", type = ArrayList.class)
})
public class LoadFlightsCommand extends AbstractCommand {

    public LoadFlightsCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public LoadFlightsCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {

        String sqlQuery = (String) context.get("sqlQuery");

        List<Flight> flights = (List<Flight>) context.get("list");

        try {
            List<Flight> list = AppConst.DAO.select(Flight.class, sqlQuery);
            for (Flight f : list) {
                flights.add(f);
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
