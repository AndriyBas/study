package com.oyster.core.controller.exception;

/**
 * Базова помилка, викидається під час помилки виконання команди
 * @author bamboo
 */
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
