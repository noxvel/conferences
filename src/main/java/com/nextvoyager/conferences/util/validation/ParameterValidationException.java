package com.nextvoyager.conferences.util.validation;

/**
 * This class represents an exception in the {@link ParameterValidator}.
 *
 * @author Stanislav Bozhevskyi
 */
public class ParameterValidationException extends Exception{
    public ParameterValidationException() {
        super();
    }

    public ParameterValidationException(String message) {
        super(message);
    }

    public ParameterValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterValidationException(Throwable cause) {
        super(cause);
    }
}
