package com.nextvoyager.conferences.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<R>{
    R apply(ResultSet rs) throws SQLException;
}