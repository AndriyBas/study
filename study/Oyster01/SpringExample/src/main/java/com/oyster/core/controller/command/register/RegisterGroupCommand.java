package com.oyster.core.controller.command.register;

import com.oyster.app.AppConst;
import com.oyster.app.model.Faculty;
import com.oyster.app.model.Group;
import com.oyster.app.model.History;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.core.controller.command.AbstractCommand;
import com.oyster.core.controller.command.Context;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.ui.Utils;

import javax.swing.*;
import java.util.UUID;

/**
 * Created by bamboo on 11.05.14.
 */


@COMMAND(key = "registerGroup")
@CONTEXT(list = {
        @PARAMETER(key = "name", type = String.class),
        @PARAMETER(key = "faculty", type = String.class),

})
public class RegisterGroupCommand extends AbstractCommand {

    public RegisterGroupCommand() {
    }

    /**
     * @param context1 params for the command registerStudent
     */
    public RegisterGroupCommand(Context context1) {
        setContext(context1);
    }

    @Override
    public void run() {


        final String facultyName = (String) context.get("faculty");
        java.util.List<Faculty> faculties = null;
        try {
            faculties = AppConst.DAO.select(Faculty.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Faculty f = (Faculty) entity;
                    return f.getName().equals(facultyName);

                }
            });
        } catch (final DAOException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, Utils.makePretty("Помилка зчитування факультету : \n" + e.getMessage()));
                }
            });
        }

        if (faculties.size() == 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, "Немає такого факультету!");
                }
            });
            return;
        }

        Group g = new Group(UUID.randomUUID(),
                faculties.get(0).getId(),
                ((String) context.get("name")).toUpperCase());
        try {
            AppConst.DAO.insert(g);
            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Створив нову групу " + g.getName()
            );
            AppConst.DAO.insert(h);
        } catch (final Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, Utils.makePretty("Помилка створення групи : \n" + e.getMessage()));
                }
            });
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
