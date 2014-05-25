package com.fiot.core.controller.command;

import com.fiot.app.AppConst;
import com.fiot.app.model.Order;
import com.fiot.core.controller.annotation.COMMAND;
import com.fiot.core.controller.annotation.CONTEXT;
import com.fiot.core.controller.annotation.PARAMETER;
import com.fiot.dao.exception.DAOException;
import com.fiot.ui.Utils;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

/**
 * команда виконує встановлення зв’язку друзі між 2 профілями
 */
@COMMAND(key = "addToFriends")
@CONTEXT(list = {
        @PARAMETER(key = "senderId", type = UUID.class),
        @PARAMETER(key = "receiverId", type = UUID.class)
})
public class AddToFriendCommand extends AbstractCommand {

    public AddToFriendCommand() {
    }

    /**
     * Конструктор
     *
     * @param context1 контекст команди
     */
    public AddToFriendCommand(Context context1) {
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
            List<Order> receivedList = AppConst.DAO.select(Order.class, "select * from REQUEST_TBL where (sender_id = \"" +
                    receiver + "\" AND receiver_id = \"" + sender + "\");");

            List<Order> sentList = AppConst.DAO.select(Order.class, "select * from REQUEST_TBL where (sender_id = \"" +
                    sender + "\" AND receiver_id = \"" + receiver + "\");");

            if (receivedList.size() > 0) {


                for (Order r : sentList) {
                    if (r != null) {
                        AppConst.DAO.delete(r);
                    }
                }
                for (Order r : receivedList) {
                    if (r != null) {
                        AppConst.DAO.delete(r);
                    }
                }

//                Company relation = null;

//                AppConst.DAO.insert(relation);
                onPost();
                return;
            } else if (sentList.size() > 0) {
                onPost();
                return;
            } else {
                Order request = new Order(
                        UUID.randomUUID(),
                        sender,
                        receiver
                );

                AppConst.DAO.insert(request);
                onPost();
                return;
            }

        } catch (DAOException e) {
            e.printStackTrace();
            Utils.showErrorDialog(null, e.getMessage());
        }


    }

    private void onPost() {
        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }

}
