package com.nextvoyager.conferences.util.emailcreator;

/**
 * This class represents an exception in the {@link EmailCreator}.
 *
 */
public class EmailConfigException extends RuntimeException{
    public EmailConfigException() {
        super();
    }

    public EmailConfigException(String message) {
        super(message);
    }

    public EmailConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailConfigException(Throwable cause) {
        super(cause);
    }
}
