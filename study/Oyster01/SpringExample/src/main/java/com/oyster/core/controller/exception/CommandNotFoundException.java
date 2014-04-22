package com.oyster.core.controller.exception;

/**
 * @author bamboo
 * @since 4/22/14 1:38 AM
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
