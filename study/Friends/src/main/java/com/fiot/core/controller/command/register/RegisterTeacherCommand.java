package com.fiot.core.controller.command.register;

import com.fiot.app.AppConst;
import com.fiot.app.model.History;
import com.fiot.app.model.Profile;
import com.fiot.app.model.Teacher;
import com.fiot.app.model.WorkerInfo;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.core.controller.command.AbstractCommand;
import com.fiot.core.controller.command.Context;
import com.fiot.ui.Utils;

import javax.swing.*;
import java.util.UUID;

/**
 * команда виконує реєстрацію викладача у системі, інформацію про якого їй передається у контексті
 * @author bamboo
 */

@COMMAND(key = "registerTeacher")
@CONTEXT(list = {
        @PARAMETER(key = "name", type = String.class),
        @PARAMETER(key = "surname", type = String.class),
        @PARAMETER(key = "birthday", type = Long.class, optional = true),
        @PARAMETER(key = "position", type = String.class),

        @PARAMETER(key = "salary", type = Integer.class),
        @PARAMETER(key = "password", type = String.class),
        @PARAMETER(key = "dateHired", type = Long.class, optional = true)
})
public class RegisterTeacherCommand extends AbstractCommand {

    public RegisterTeacherCommand() {
    }

    /**
     * Конструктор
     * @param context1 контекст команди
     */
    public RegisterTeacherCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {


        Profile pTeacher = new Profile(
                UUID.randomUUID(),
                (String) context.get("name"),
                (String) context.get("surname"),
                (String) context.get("password"),
                (Long) context.get("birthday")
        );

        WorkerInfo wTeacher = new WorkerInfo(
                UUID.randomUUID(),
                (String) context.get("position"),
                (Integer) context.get("salary"),
                (Long) context.get("dateHired")
        );

        Teacher teacher = new Teacher(
                UUID.randomUUID(),
                pTeacher.getId(),
                wTeacher.getId()
        );

        try {
            AppConst.DAO.insert(pTeacher);
            AppConst.DAO.insert(wTeacher);
            AppConst.DAO.insert(teacher);

            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Додав викладача " + pTeacher.toString()
            );
            AppConst.DAO.insert(h);

        } catch (final Exception e) {
            e.printStackTrace();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null,
                            Utils.makePretty("Помилка створення викладача : \n" + e.getMessage()));
                }
            });
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
