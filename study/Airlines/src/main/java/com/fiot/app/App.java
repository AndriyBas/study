package com.fiot.app;

import com.fiot.config.AppConfig;
import com.fiot.config.ConfigReader;
import com.fiot.config.impl.JSONConfigReader;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.*;
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

        executor.addCommand(LogInCommand.class);
        executor.addCommand(RegisterUserCommand.class);
        executor.addCommand(LoadFlightsCommand.class);
        executor.addCommand(UpdateUserCommand.class);

        executor.addCommand(UpdateFlight.class);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginForm();

    }
}
