package com.nextvoyager.conferences.model.dao;

import lombok.Data;

@Data
public class ValueDAO {
    Object value;
    int type;

    public ValueDAO(Object value, int type) {
        this.value = value;
        this.type = type;
    }
}
