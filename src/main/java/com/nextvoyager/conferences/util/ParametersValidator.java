package com.nextvoyager.conferences.util;

public class ParametersValidator {

    public static final String REGEXP_EMAIL = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String REGEXP_USER_NAME = "[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\\s'-]{1,60}";
    public static final String REGEXP_PASSWORD = ".{3,60}";

    private ParametersValidator(){};


}
