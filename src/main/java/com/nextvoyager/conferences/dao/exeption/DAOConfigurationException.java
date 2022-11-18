package com.nextvoyager.conferences.dao.exeption;

public class DAOConfigurationException extends RuntimeException{
    public DAOConfigurationException() {
        super();
    }

    public DAOConfigurationException(String message) {
        super(message);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}
