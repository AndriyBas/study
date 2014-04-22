package com.oyster.core.controller.exception;

/**
 * @author bamboo
 * @since 4/22/14 1:36 AM
 */
public class InvalidCommandParameterException extends CommandException {
    public InvalidCommandParameterException() {
        super();
    }

    public InvalidCommandParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandParameterException(String message) {
        super(message);
    }

    public InvalidCommandParameterException(Throwable cause) {
        super(cause);
    }
}
