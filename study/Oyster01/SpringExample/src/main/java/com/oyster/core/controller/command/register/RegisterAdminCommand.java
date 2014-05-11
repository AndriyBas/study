package com.oyster.core.controller.command.register;

import com.oyster.app.AppConst;
import com.oyster.app.model.Admin;
import com.oyster.app.model.History;
import com.oyster.app.model.Profile;
import com.oyster.app.model.WorkerInfo;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.core.controller.command.AbstractCommand;
import com.oyster.core.controller.command.Context;
import com.oyster.ui.Utils;

import javax.swing.*;
import java.util.UUID;

/**
 * @author bamboo
 * @since 4/21/14 11:33 PM
 */


@COMMAND(key = "registerAdmin")
@CONTEXT(list = {
        @PARAMETER(key = "name", type = String.class),
        @PARAMETER(key = "surname", type = String.class),
        @PARAMETER(key = "birthday", type = Long.class, optional = true),
        @PARAMETER(key = "position", type = String.class),

        @PARAMETER(key = "salary", type = Integer.class),
        @PARAMETER(key = "password", type = String.class),
        @PARAMETER(key = "dateHired", type = Long.class, optional = true)
})
public class RegisterAdminCommand extends AbstractCommand {

    public RegisterAdminCommand() {
    }

    /**
     * @param context1 params for the command registerStudent
     */
    public RegisterAdminCommand(Context context1) {
        setContext(context1);
    }

    @Override
    public void run() {


        Profile pAdmin = new Profile(
                UUID.randomUUID(),
                (String) context.get("name"),
                (String) context.get("surname"),
                (String) context.get("password"),
                (Long) context.get("birthday")
        );

        WorkerInfo wAdmin = new WorkerInfo(
                UUID.randomUUID(),
                (String) context.get("position"),
                (Integer) context.get("salary"),
                (Long) context.get("dateHired")
        );

        Admin admin = new Admin(
                UUID.randomUUID(),
                pAdmin.getId(),
                wAdmin.getId()
        );

        try {
            AppConst.DAO.insert(pAdmin);
            AppConst.DAO.insert(wAdmin);
            AppConst.DAO.insert(admin);

            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Додав адміністратора " + pAdmin.toString()
            );
            AppConst.DAO.insert(h);

        } catch (final Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null,
                            Utils.makePretty("Помилка створення адміністратора : \n" + e.getMessage()));
                }
            });
            ;
        }


        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }
}
