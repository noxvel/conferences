package com.nextvoyager.conferences.model.dao.exeption;

public class DAOException extends RuntimeException{
    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
//        super(cause);
        System.out.println(cause.toString());
    }
}
