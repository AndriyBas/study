package com.fiot.app;

import com.fiot.config.AppConfig;
import com.fiot.config.ConfigReader;
import com.fiot.config.impl.JSONConfigReader;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.AddToFriendCommand;
import com.fiot.core.controller.command.LoadUsersCommand;
import com.fiot.core.controller.command.LogInCommand;
import com.fiot.dao.impl.DAOCRUDJdbc;
import com.fiot.ui.LoginForm;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

/**
 * Клас налаштовує програму,
 * зававнтажує файли конфігурацій,
 * запускає контролер, відкриває зв’язок із базою даних
 * та запускає головне вікно програми
 */
public class App {

    /**
     * метод запуску
     *
     * @param args аргументи командної стрічки
     */
    public static void main(String[] args) {
        AppConst.CONTEXT = new ClassPathXmlApplicationContext("Spring-Module.xml");
        AppConst.DAO = DAOCRUDJdbc.getInstance(AppConst.CONTEXT);

        AppConst.APP_CONFIG = AppConfig.getInstance();

        String inputFile = "airlinesConfig.json";

        ConfigReader reader = new JSONConfigReader(AppConst.APP_CONFIG);
        reader.loadFromFile(inputFile);

        CommandExecutor executor = CommandExecutor.getInstance();

        executor.addCommand(AddToFriendCommand.class);
        executor.addCommand(LogInCommand.class);

        executor.addCommand(LoadUsersCommand.class);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginForm();

    }
}
