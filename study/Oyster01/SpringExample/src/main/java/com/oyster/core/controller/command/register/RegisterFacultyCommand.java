package com.oyster.core.controller.command.register;

import com.oyster.app.AppConst;
import com.oyster.app.model.Faculty;
import com.oyster.app.model.History;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.core.controller.command.AbstractCommand;
import com.oyster.core.controller.command.Context;
import com.oyster.ui.Utils;

import javax.swing.*;
import java.util.UUID;

/**
 * Created by bamboo on 11.05.14.
 */


@COMMAND(key = "registerFaculty")
@CONTEXT(list = {
        @PARAMETER(key = "name", type = String.class)
})
public class RegisterFacultyCommand extends AbstractCommand {

    public RegisterFacultyCommand() {
    }

    /**
     * @param context1 params for the command registerStudent
     */
    public RegisterFacultyCommand(Context context1) {
        setContext(context1);
    }

    @Override
    public void run() {


        Faculty fac = new Faculty(UUID.randomUUID(), ((String) context.get("name")).toUpperCase());
        try {
            AppConst.DAO.insert(fac);
            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Створив новий факультет " + fac.getName()
            );
            AppConst.DAO.insert(h);
        } catch (final Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, Utils.makePretty("Помилка створення факультету : \n" + e.getMessage()));
                }
            });
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
