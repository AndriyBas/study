package com.oyster.core.controller.exception;

/**
 * @author bamboo
 * @since 4/22/14 1:37 AM
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
