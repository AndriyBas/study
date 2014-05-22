package com.fiot.core.controller.exception;

/**
 * Базова помилка, викидається коли команда не проходить валідації
 *
 * @author bamboo
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
