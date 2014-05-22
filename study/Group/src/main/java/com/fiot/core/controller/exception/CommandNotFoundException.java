package com.fiot.core.controller.exception;

/**
 * Базова помилка, викидається коли CommandExecutor не знаходить
 * команди із даним ключем
 *
 * @author bamboo
 */
public class CommandNotFoundException extends CommandException {

    public CommandNotFoundException() {
        super();
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(Throwable cause) {
        super(cause);
    }
}
