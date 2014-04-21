package com.oyster.core.controller;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author bamboo
 * @since 4/21/14 11:30 PM
 */
public class CommandExecutor {


    private static volatile CommandExecutor sCommandExecutor;

    private Executor singleThreadExecutor;

    /**
     * creates newSinglethreadExecutor
     */
    private CommandExecutor() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * retrieves an instance of singleton
     *
     * @return singleton instance of CommandExecutor
     */
    public CommandExecutor getInstance() {
        // double checked synchronization for thread safety
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
