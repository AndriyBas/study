package com.oyster.app;

import com.oyster.config.AppConfig;
import com.oyster.config.ConfigReader;
import com.oyster.config.impl.JSONConfigReader;
import com.oyster.core.controller.CommandExecutor;
import com.oyster.core.controller.command.DeleteIProfileCommand;
import com.oyster.core.controller.command.LoadHistoryCommand;
import com.oyster.core.controller.command.LoadScheduleCommand;
import com.oyster.core.controller.command.LogInCommand;
import com.oyster.core.controller.command.register.*;
import com.oyster.dao.impl.DAOCRUDJdbc;
import com.oyster.ui.LoginFrame;
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

        String inputFile = "kpiCityConfig.json";

        ConfigReader reader = new JSONConfigReader(AppConst.APP_CONFIG);
        reader.loadFromFile(inputFile);

        CommandExecutor executor = CommandExecutor.getInstance();

        executor.addCommand(RegisterStudentCommand.class);
        executor.addCommand(RegisterAdminCommand.class);
        executor.addCommand(RegisterTeacherCommand.class);
        executor.addCommand(RegisterGroupCommand.class);
        executor.addCommand(RegisterFacultyCommand.class);
        executor.addCommand(RegisterSubjectCommand.class);

        executor.addCommand(DeleteIProfileCommand.class);
        executor.addCommand(LogInCommand.class);

        executor.addCommand(LoadHistoryCommand.class);
        executor.addCommand(LoadScheduleCommand.class);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame loginFrame = new LoginFrame();


        int width = (Integer) AppConst.APP_CONFIG.getValue("logInScreenWidth");
        int height = (Integer) AppConst.APP_CONFIG.getValue("logInScreenHeight");

        loginFrame.setSize(width, height);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);

        loginFrame.setVisible(true);
    }
}
