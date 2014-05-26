package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.Flight;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;
import com.fiot.ui.Utils;

import javax.swing.*;
import java.util.UUID;

/**
 * команда виконує завантаження користувачів виконуюючи SQL-запит, що їй передається
 */

@COMMAND(key = "updateFlight")
@CONTEXT(list = {
        @PARAMETER(key = "id", type = UUID.class),
        @PARAMETER(key = "dep_air_id", type = UUID.class),
        @PARAMETER(key = "arr_air_id", type = UUID.class),
        @PARAMETER(key = "dep_time", type = Long.class),
        @PARAMETER(key = "arr_time", type = Long.class),
        @PARAMETER(key = "class", type = String.class),
        @PARAMETER(key = "price", type = Long.class)
})
public class UpdateFlight extends AbstractCommand {

    public UpdateFlight() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public UpdateFlight(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {


        UUID id = (UUID) context.get("id");
        UUID depId = (UUID) context.get("dep_air_id");
        UUID arrId = (UUID) context.get("arr_air_id");
        Long depTime = (Long) context.get("dep_time");
        Long arrTime = (Long) context.get("arr_time");
        String classType = (String) context.get("class");
        Long price = (Long) context.get("price");


        Flight flight = new Flight(
                id,
                depId,
                arrId,
                depTime,
                arrTime,
                classType,
                price
        );

        try {
            AppConst.DAO.replace(flight);

        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
