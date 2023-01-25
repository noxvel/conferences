package com.nextvoyager.conferences.model.dao;

import lombok.Data;

/**
 * The data class containing the value and type of the element to be inserted into the database.
 *
 * @author Stanislav Bozhevskyi
 */
@Data
public class ValueDAO {
    Object value;
    int type;

    public ValueDAO(Object value, int type) {
        this.value = value;
        this.type = type;
    }
}
