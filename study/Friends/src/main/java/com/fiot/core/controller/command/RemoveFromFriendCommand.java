package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.FriendsRelation;
import com.fiot.app.model.Requests;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;
import com.fiot.ui.Utils;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

/**
 * команда виконує видалення профілю, що передається ії параметром
 */
@COMMAND(key = "removeFromFriends")
@CONTEXT(list = {
        @PARAMETER(key = "senderId", type = UUID.class),
        @PARAMETER(key = "receiverId", type = UUID.class)
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

        UUID sender = (UUID) context.get("senderId");
        UUID receiver = (UUID) context.get("receiverId");
        try {
            List<Requests> receivedList = AppConst.DAO.select(Requests.class, "select * from REQUEST_TBL where (sender_id = \"" +
                    receiver + "\" AND receiver_id = \"" + sender + "\");");

            List<Requests> sentList = AppConst.DAO.select(Requests.class, "select * from REQUEST_TBL where (sender_id = \"" +
                    sender + "\" AND receiver_id = \"" + receiver + "\");");

            List<FriendsRelation> relationList = AppConst.DAO.select(FriendsRelation.class, "select * from FRIEND_RELATION_TBL where (" +
                    "( first_id = \"" + sender + "\" AND second_id = \"" + receiver + "\" ) OR " +
                    "( first_id = \"" + receiver + "\" AND second_id = \"" + sender + "\" ) );");


            for (Requests r : sentList) {
                if (r != null) {
                    AppConst.DAO.delete(r);
                }
            }
            for (Requests r : receivedList) {
                if (r != null) {
                    AppConst.DAO.delete(r);
                }
            }

            for (FriendsRelation r : relationList) {
                if (r != null) {
                    AppConst.DAO.delete(r);
                }
            }

        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }
        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
