package com.nextvoyager.conferences.model.dao.utils;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.ResultSetMapper;
import com.nextvoyager.conferences.model.dao.ValueDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

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

    public static <T> T processRS(ResultSet resultSet, ResultSetMapper<T> map) throws SQLException {
        T element = null;
        if (resultSet.next()) {
            element = map.apply(resultSet);
        }
        return element;
    }

    public static <T> void processListRS(ResultSet countRS, ResultSet listRS, ListWithCount<T> result,
                                         String allCountField, ResultSetMapper<T> map) throws SQLException {
        if (countRS.next()) {
            result.setCount(countRS.getInt(allCountField));
            List<T> list = result.getList();
            while (listRS.next()) {
                list.add(map.apply(listRS));
            }
        }
    }

    public static <T> void processListRS(ResultSet listRS, List<T> result, ResultSetMapper<T> map) throws SQLException {
        while (listRS.next()) {
            result.add(map.apply(listRS));
        }
    }

    public static Timestamp toSqlTimestamp(LocalDateTime dateTime) {
        return (dateTime != null) ? Timestamp.valueOf(dateTime) : null;
    }

}
