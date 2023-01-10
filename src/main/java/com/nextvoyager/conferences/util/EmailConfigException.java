package com.nextvoyager.conferences.util;

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
