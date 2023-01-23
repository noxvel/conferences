package com.nextvoyager.conferences.util.validation;

import lombok.Data;

@Data
public class ValidateObject {

    private String parameter;
    private boolean allowEmpty;
    private ValidateRegExp regExp;

    public ValidateObject(String parameter) {
        this.parameter = parameter;
    }

    public ValidateObject(String parameter, boolean allowEmpty) {
        this.parameter = parameter;
        this.allowEmpty = allowEmpty;
    }

    public ValidateObject(String parameter, ValidateRegExp regExp) {
        this.parameter = parameter;
        this.regExp = regExp;
    }

}
