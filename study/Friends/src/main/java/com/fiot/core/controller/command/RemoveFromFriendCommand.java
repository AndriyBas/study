package com.fiot.core.controller.command;

import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;

import javax.swing.*;

/**
 * команда виконує видалення профілю, що передається ії параметром
 */
@COMMAND(key = "removeFromFriends")
@CONTEXT(list = {
        @PARAMETER(key = "senderId"),
        @PARAMETER(key = "receiverId")
})
public class RemoveFromFriendCommand extends AbstractCommand {

    public RemoveFromFriendCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public RemoveFromFriendCommand(Context context1) {
        setContext(context1);
    }

    /**
     * виконує роботу команди
     */
    @Override
    public void run() {


        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
