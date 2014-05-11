package com.oyster.ui;

import com.oyster.app.AppConst;
import com.oyster.app.model.History;
import com.oyster.app.model.Profile;
import com.oyster.dao.exception.DAOException;
import com.oyster.dao.impl.DAOCRUDJdbc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by bamboo on 10.05.14.
 */
public class HistoryTab {

    private JFrame frame;
    private JComboBox comboBox;
    private JList historyList;

    private DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(AppConst.CONTEXT);

    private Map<UUID, Profile> profiles;

    public HistoryTab(JFrame frame, JComboBox comboBox, JList historyList) {
        this.frame = frame;
        this.comboBox = comboBox;
        this.historyList = historyList;

        profiles = new HashMap<>();

        hardcoreInit();
    }

    private void hardcoreInit() {

        comboBox.setModel(new DefaultComboBoxModel(new Object[]{"Власна", "Уся"}));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboboxChangedAction();
            }
        });

        try {
            List<Profile> list = x.select(Profile.class, "");
            for (Profile p : list) {
                profiles.put(p.getId(), p);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    private void comboboxChangedAction() {

        switch (comboBox.getSelectedIndex()) {
            case 0:

                loadHistory("select * from HISTORY_TBL where author_id = \"" +
                        AppConst.getCurrentAdmin().getProfileId() + "\";");
                break;

            case 1:

                loadHistory("");
                break;
        }

    }

    private void loadHistory(String sql) {


        java.util.List<History> histories = null;

        try {
            histories = x.select(History.class, sql);
            for (History h : histories) {
                h.setAuthor(profiles.get(h.getAuthorId()));
            }

        } catch (DAOException e) {
            e.printStackTrace();
        }

        DefaultListModel<History> model = new DefaultListModel<>();
        for (History h : histories) {
            model.addElement(h);
        }

        historyList.setModel(model);

        frame.validate();
        frame.repaint();

    }


}