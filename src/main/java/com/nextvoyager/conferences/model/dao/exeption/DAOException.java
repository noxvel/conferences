package com.nextvoyager.conferences.model.dao.exeption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DAOException extends RuntimeException{
    private static final Logger LOG = LogManager.getLogger(DAOException.class);

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
        LOG.error(cause.toString());
    }
}
