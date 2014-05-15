package com.fiot.dao.exception;

/**
 * Базова помилка, викидається під час помилки зчитування/запису
 * @author bamboo
 */
public class DAOException extends Exception {

    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

}
