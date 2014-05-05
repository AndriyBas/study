package com.oyster.core.controller.command;

import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;

import javax.swing.*;

/**
 * @author bamboo
 * @since 4/21/14 11:33 PM
 */

@COMMAND(key = "registerStudent")
@CONTEXT(list = {
        @PARAMETER(key = "name", type = String.class),
        @PARAMETER(key = "surname", type = String.class),
        @PARAMETER(key = "birthday", type = Long.class, optional = true),
        @PARAMETER(key = "faculty", type = String.class),
        @PARAMETER(key = "group", type = String.class),
        @PARAMETER(key = "course", type = Integer.class),
        @PARAMETER(key = "comment", type = String.class, optional = true),
        @PARAMETER(key = "bookNum", type = Integer.class)
})
public class RegisterStudentCommand extends AbstractCommand {

    public RegisterStudentCommand() {
    }

    /**
     * @param context1 params for the command registerStudent
     */
    public RegisterStudentCommand(Context context1) {
        setContext(context1);
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                Thread.sleep(200);
                System.out.println("Executing register : " + (i * 10) + " %");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }
}
