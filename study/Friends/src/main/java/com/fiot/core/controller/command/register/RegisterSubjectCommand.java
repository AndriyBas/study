package com.fiot.core.controller.command.register;

import com.fiot.app.AppConst;
import com.fiot.app.model.History;
import com.fiot.app.model.Subject;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.core.controller.command.AbstractCommand;
import com.fiot.core.controller.command.Context;
import com.fiot.ui.Utils;

import javax.swing.*;
import java.util.UUID;
/**
 * команда виконує реєстрацію предмету у системі, інформацію про якого їй передається у контексті
 * @author bamboo
 */

@COMMAND(key = "registerSubject")
@CONTEXT(list = {
        @PARAMETER(key = "name", type = String.class)
})
public class RegisterSubjectCommand extends AbstractCommand {

    public RegisterSubjectCommand() {
    }

    /**
     * Конструктор
     * @param context1 контекст команди
     */
    public RegisterSubjectCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {


        Subject subject = new Subject(UUID.randomUUID(), ((String) context.get("name")).toUpperCase());
        try {
            AppConst.DAO.insert(subject);
            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Створив новий предмет " + subject.getName()
            );
            AppConst.DAO.insert(h);
        } catch (final Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, Utils.makePretty("Помилка створення предмету : \n" + e.getMessage()));
                }
            });
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
