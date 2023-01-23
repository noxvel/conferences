package com.nextvoyager.conferences.util.validation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class ParameterValidator {

    private ParameterValidator(){};

    public static void validate(HttpServletRequest req, ValidateObject... validateObjects) throws ServletException {
        List<String> errorMessages = new ArrayList<>();

        for (ValidateObject val: validateObjects) {
            try {
                validateParameter(req,val);
            } catch (ParameterValidationException e) {
                errorMessages.add(e.getMessage());
            }
        }

        if (!errorMessages.isEmpty()) {
            throw new ServletException("Invalid input values: " + String.join(",\\n", errorMessages));
        }
    }

    private static void validateParameter(HttpServletRequest req, ValidateObject val) throws ParameterValidationException {
        String param = req.getParameter(val.getParameter());
        if (param == null ) {
            throw new ParameterValidationException("Please enter - " + val.getParameter());
        }
        if (!val.isAllowEmpty()) {
            if (param.trim().isEmpty()) {
                throw new ParameterValidationException("Please enter - " + val.getParameter());
            }
            if (val.getRegExp() != null) {
                if (!param.matches(val.getRegExp().getValue())) {
                    throw new ParameterValidationException("Please enter " + val.getRegExp().getErrorMsg() +
                            " for parameter - " + val.getParameter());
                }
            }
        }
    }

}
