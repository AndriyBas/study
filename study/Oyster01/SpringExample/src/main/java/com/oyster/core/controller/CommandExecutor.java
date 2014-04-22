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
 * @author bamboo
 * @since 4/21/14 11:30 PM
 */
public class CommandExecutor {

    private static final int THREAD_NUM = 1;

    /**
     * Значение переменной, объявленной без volatile, может кэшироваться отдельно
     * для каждого потока, и значение из этого кэша может различаться для каждого из них. Объявление переменной
     * с ключевым словом volatile отключает для неё такое кэширование и все запросы к переменной будут направляться
     * непосредственно в память.
     */
    private static volatile CommandExecutor sCommandExecutor;

    // Executor Service to run commands in it
    private ExecutorService threadExecutorService;

    private Map<String, Class> commandContainer;

    /**
     * creates newSinglethreadExecutor
     */
    private CommandExecutor() {
        threadExecutorService = Executors.newFixedThreadPool(THREAD_NUM);
        commandContainer = new HashMap<>();
    }

    /**
     * retrieves an instance of singleton
     *
     * @return singleton instance of CommandExecutor
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
     * execute the command
     *
     * @param action
     * @param params
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

    private Class findCommandByAction(String action) {
        return commandContainer.get(action);
    }

    /**
     * tells the executor that new Command is available
     *
     * @param command class of the new command
     */
    public void addCommand(Class command) {
        COMMAND c = (COMMAND) command.getAnnotation(COMMAND.class);
        commandContainer.put(c.key(), command);
    }

    /**
     * makes sure noone can clone singleton instance
     *
     * @return nothing
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("CommandExecutor is a singleton");
    }
}
