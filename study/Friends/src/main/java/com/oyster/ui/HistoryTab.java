package com.oyster.ui;

import com.oyster.app.AppConst;
import com.oyster.app.model.History;
import com.oyster.app.model.Profile;
import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.Context;
import com.oyster.dao.exception.DAOException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Клас відповідає табові, що відображає історію коритсувачів
 */
public class HistoryTab {

    private JFrame frame;
    private JComboBox comboBox;
    private JList historyList;

    java.util.List<History> histories;

    private Map<UUID, Profile> profiles;

    /**
     * Конструктор, що приймає елементи інтерфейсу для подальших операцій із ними
     *
     * @param frame       вікно програми
     * @param comboBox    елемент вибору типу історії
     * @param historyList список історії
     */
    public HistoryTab(JFrame frame, JComboBox comboBox, JList historyList) {
        this.frame = frame;
        this.comboBox = comboBox;
        this.historyList = historyList;

        profiles = new HashMap<>();

        hardcoreInit();
    }

    /**
     * ініціалізує компоненти
     */
    private void hardcoreInit() {

        comboBox.setModel(new DefaultComboBoxModel(new Object[]{"Власна", "Уся"}));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboboxChangedAction();
            }
        });

        try {
            List<Profile> list = AppConst.DAO.select(Profile.class, "");
            for (Profile p : list) {
                profiles.put(p.getId(), p);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        comboBox.setSelectedIndex(0);
    }

    /**
     * реагує на зміну опції Власна/Уся історія
     */
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

    /**
     * Завантажує історію користувача, виконуюючи sql-запит
     *
     * @param sql SQL-запит для виконання
     */
    private void loadHistory(String sql) {

        Context c = new Context();
        histories = new ArrayList<>();
        c.put("list", histories);
        c.put("sqlQuery", sql);

        try {
            CommandExecutor.getInstance().execute("loadHistory", c, new Runnable() {
                @Override
                public void run() {

                    System.out.println(histories.size());

                    for (History h : histories) {
                        h.setAuthor(profiles.get(h.getAuthorId()));
                    }
                    DefaultListModel<History> model = new DefaultListModel<>();
                    for (History h : histories) {
                        model.addElement(h);
                    }

                    historyList.setModel(model);
                    frame.validate();
                    frame.repaint();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}