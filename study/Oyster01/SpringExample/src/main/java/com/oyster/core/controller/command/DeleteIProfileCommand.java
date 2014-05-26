package com.oyster.core.controller.command;

import com.oyster.app.AppConst;
import com.oyster.app.model.History;
import com.oyster.app.model.IProfile;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.dao.exception.DAOException;

import java.util.UUID;

/**
 * команда виконує видалення профілю, що передається ії параметром
 */
@COMMAND(key = "deleteIProfile")
@CONTEXT(list = {
        @PARAMETER(key = "profile", type = IProfile.class)
})
public class DeleteIProfileCommand extends AsyncCommand<Void, Boolean> {

    @Override
    protected Boolean doInBackGround(Context context) {

        IProfile currentPerson = (IProfile) context.get("profile");

        try {
            AppConst.DAO.delete(currentPerson.getProfile());
            AppConst.DAO.delete(currentPerson);

            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Видалив користувача " + currentPerson.getProfile().toString()
            );
            AppConst.DAO.insert(h);

        } catch (DAOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        System.out.println("Deleted " + (aBoolean ? "" : "un") + "successfully !");
    }
}
