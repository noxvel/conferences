package com.nextvoyager.conferences.model.dao.exeption;

/**
 * This class represents an exception in the DAO configuration which cannot be resolved at runtime,
 * such as a missing resource in the classpath, a missing property in the properties file, etc.
 *
 */
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
