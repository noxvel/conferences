package com.nextvoyager.conferences.util.validation;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Enumeration of regular expressions and error messages to them.
 *
 * @author Stanislav Bozhevskyi
 */
@Getter
public enum ValidateRegExp {

    REGEXP_EMAIL("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            "correct email address"),
    REGEXP_USER_NAME("[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\\s'-]{1,60}",
            "alphabetical characters from 1 to 60"),
    REGEXP_PASSWORD(".{3,60}",
            "from 3 to 60 symbols"),
    REGEXP_DATETIME("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$",
            "correct date"),
    REGEXP_ID("\\d+",
            "correct id"),
    REGEXP_BOOLEAN("(true|false)",
            "true or false value"),
    REGEXP_USER_ROLE(Arrays.stream(User.Role.values())
                .map(User.Role::name)
                .collect(Collectors.joining("|","(",")")),
            "correct user role"),
    REGEXP_REPORT_STATUS(Arrays.stream(Report.Status.values())
            .map(Report.Status::name)
                .collect(Collectors.joining("|","(",")")),
            "correct report status");

    private final String value;
    private final String errorMsg;

    ValidateRegExp(String value, String errorMsg) {
        this.value = value;
        this.errorMsg = errorMsg;
    }
}
