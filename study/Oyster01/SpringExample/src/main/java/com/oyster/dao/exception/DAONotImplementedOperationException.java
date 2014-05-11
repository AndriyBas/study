package com.oyster.dao.exception;

/**
 * Базова помилка, викидається під час спроби доступу до методу, що не реалізований
 * @author bamboo
 */
public class DAONotImplementedOperationException extends DAOException {

    public DAONotImplementedOperationException() {
        super();
    }

    public DAONotImplementedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAONotImplementedOperationException(String message) {
        super(message);
    }

    public DAONotImplementedOperationException(Throwable cause) {
        super(cause);
    }

}
