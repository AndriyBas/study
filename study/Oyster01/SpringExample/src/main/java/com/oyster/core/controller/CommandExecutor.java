package com.oyster.core.controller;

import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.command.AbstractCommand;
import com.oyster.core.controller.command.Context;
import com.oyster.core.controller.exception.CommandNotFoundException;
import com.oyster.core.controller.exception.InvalidCommandParameterException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Клас відповідає за реєстрацію, валідацію та виконання команд,
 * реалізує паттерн Singleton
 *
 * @author bamboo
 */
public class CommandExecutor {

    private static final int THREAD_NUM = 1;

    /*
     * Значение переменной, объявленной без volatile, может кэшироваться отдельно
     * для каждого потока, и значение из этого кэша может различаться для каждого из них. Объявление переменной
     * с ключевым словом volatile отключает для неё такое кэширование и все запросы к переменной будут направляться
     * непосредственно в память.
     */
    private static volatile CommandExecutor sCommandExecutor;

    private ExecutorService threadExecutorService;

    private Map<String, Class> commandContainer;

    /**
     * створює екземпляр класу CommandExecutor
     */
    private CommandExecutor() {
        threadExecutorService = Executors.newFixedThreadPool(THREAD_NUM);
        commandContainer = new HashMap<>();
    }

    /**
     * повертає змінну класу CommandExecutor
     *
     * @return екземпляр класу CommandExecutor
     */
    public static CommandExecutor getInstance() {
        // double checked locking for thread safety
        if (sCommandExecutor == null) {
            synchronized (CommandExecutor.class) {
                if (sCommandExecutor == null) {
                    sCommandExecutor = new CommandExecutor();
                }
            }
        }
        return sCommandExecutor;
    }

    /**
     * виконує команду у  фоновому режимі
     *
     * @param action ключ команди
     * @param params контекст команди
     */
    public void execute(String action, Context params, Runnable onPostExecute) throws CommandNotFoundException, InstantiationException, IllegalAccessException, InvalidCommandParameterException {

        Class commandClass = findCommandByAction(action);

        if (commandClass == null) {
            throw new CommandNotFoundException();
        }

        AbstractCommand c = (AbstractCommand) commandClass.newInstance();

        Validator.validate(c.getClass(), params);

        c.setContext(params);

        c.setOnPostExecute(onPostExecute);

        threadExecutorService.submit(c);

    }

    /**
     * шукає команду за ключем
     *
     * @param action ключ команди
     * @return команду, якщо знаходить, інакше  null
     */
    private Class findCommandByAction(String action) {
        return commandContainer.get(action);
    }

    /**
     * реєструє нову команду в CommandExecutor
     *
     * @param command class of the new command
     */
    public void addCommand(Class command) {
        COMMAND c = (COMMAND) command.getAnnotation(COMMAND.class);
        commandContainer.put(c.key(), command);
    }

    /**
     * викидає помилку при спробі клонувати об’єкт, оскільки CommandExecutor
     * повинен бути одиночкою
     *
     * @return нічого
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("CommandExecutor is a singleton");
    }
}
