package com.oyster.core.controller.command;

import com.oyster.app.AppConst;
import com.oyster.app.model.*;
import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.ui.Utils;

import javax.swing.*;
import java.util.UUID;

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
        @PARAMETER(key = "password", type = String.class),
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


        Profile pStudent = new Profile(
                UUID.randomUUID(),
                (String) context.get("name"),
                (String) context.get("surname"),
                (String) context.get("password"),
                (Long) context.get("birthday")
        );

        final String groupName = (String) context.get("group");
        java.util.List<Group> groups = null;
        try {
            groups = AppConst.DAO.select(Group.class, new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    Group g = (Group) entity;
                    return g.getName().equals(groupName);
                }
            });
            for (Group gg : groups) {
                gg.setFaculty((Faculty) AppConst.DAO.read(Faculty.class, gg.getFacultyId()));
            }
        } catch (final DAOException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, Utils.makePretty("Помилка зчитування групи : \n" + e.getMessage()));
                }
            });
        }

        if (groups.size() == 0) {
            Utils.showErrorDialog(null, "Немає такої групи!");
            return;
        }

        final Group group = groups.get(0);

        if (!group.getFaculty().getName().equals((String) context.get("faculty"))) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, "Немає такої групи на цьому факультеті!");
                }
            });
            return;
        }


        Student student = new Student(
                UUID.randomUUID(),
                pStudent.getId(),
                groups.get(0).getId(),
                (Integer) context.get("course"),
                (Integer) context.get("bookNum")
        );

        try {
            AppConst.DAO.insert(pStudent);
            AppConst.DAO.insert(student);

            History h = new History(
                    UUID.randomUUID(),
                    AppConst.getCurrentAdmin().getProfileId(),
                    "Додав студента " + pStudent.toString()
            );
            AppConst.DAO.insert(h);
        } catch (final DAOException e) {
            e.printStackTrace();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Utils.showErrorDialog(null, Utils.makePretty("Помилка створення студента : \n" + e.getMessage()));
                }
            });


        }

        if (getOnPostExecute() != null) {
            SwingUtilities.invokeLater(getOnPostExecute());
        }
    }
}
