package com.nextvoyager.conferences.util.validation;

import jakarta.servlet.http.HttpServletRequest;

public class ParameterValidator {

    public static final String REGEXP_EMAIL = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String REGEXP_USER_NAME = "[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\\s'-]{1,60}";
    public static final String REGEXP_PASSWORD = ".{3,60}";

    private ParameterValidator(){};

    public static String getCommonStringParameter(HttpServletRequest req,
                                                  String fieldName, boolean allowEmpty) throws ParameterValidationException{
        String param = req.getParameter(fieldName);
        if (param == null ) {
            throw new ParameterValidationException("Please enter - " + fieldName);
        }
        if (!allowEmpty) {
            if (param.trim().isEmpty()) {
                throw new ParameterValidationException("Please enter - " + fieldName);
            }
        }

        return param;
    }

    public static String getEmailParameter(HttpServletRequest req, String fieldName) throws ParameterValidationException{
        String param = req.getParameter(fieldName);
        if (param == null || param.trim().isEmpty()) {
            throw new ParameterValidationException("Please enter email");
        } else if (!param.matches(REGEXP_EMAIL)) {
            throw new ParameterValidationException("Please enter correct email address");
        }
        return param;
    }

    public static String getUserNameParameter(HttpServletRequest req, String fieldName) throws ParameterValidationException{
        String param = req.getParameter(fieldName);
        if (param == null || param.trim().isEmpty()) {
            throw new ParameterValidationException("Please enter - " + fieldName);
        } else if (!param.matches(REGEXP_USER_NAME)) {
            throw new ParameterValidationException("Please enter alphabetical characters from 1 to 60 for field - " + fieldName);
        }
        return param;
    }

    public static String getPasswordParameter(HttpServletRequest req, String fieldName) throws ParameterValidationException{
        String param = req.getParameter(fieldName);
        if (param == null || param.trim().isEmpty()) {
            throw new ParameterValidationException("Please enter - " + fieldName);
        } else if (!param.matches(REGEXP_PASSWORD)) {
            throw new ParameterValidationException("Please enter from 3 to 60 symbols - " + fieldName);
        }
        return param;
    }

}
