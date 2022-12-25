package com.nextvoyager.conferences.model.dao.utils;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

public class DAOUtil {

    // Constructors -------------------------------------------------------------------------------

    private DAOUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     * @param connection The Connection to create the PreparedStatement from.
     * @param sql The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys Set whether to return generated keys or not.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during creating the PreparedStatement.
     */
    public static PreparedStatement prepareStatement
    (Connection connection, String sql, boolean returnGeneratedKeys, ValueDAO... values)
            throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        setValues(statement, values);
        return statement;
    }

    /**
     * Set the given parameter values in the given PreparedStatement.
     * @param statement The PreparedStatement to set the given parameter values in.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during setting the PreparedStatement values.
     */
    public static void setValues(PreparedStatement statement, ValueDAO... values)
            throws SQLException
    {
        int sConuter = 0;
        for (ValueDAO value : values) {
            if (value != null) {
                statement.setObject(++sConuter, value.getValue(), value.getType());
            }
        }
    }

    /**
     * Converts the given java.util.Date to java.sql.Date.
     * @param date The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static Date toSqlDate(java.util.Date date) {
        return (date != null) ? new Date(date.getTime()) : null;
    }

    @Getter
    @Setter
    public static class ValueDAO {
        Object value;
        int type;

        public ValueDAO(Object value, int type) {
            this.value = value;
            this.type = type;
        }

    }

}
