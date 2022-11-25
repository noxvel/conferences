package com.nextvoyager.conferences.dao.event;

import com.nextvoyager.conferences.controller.MainPageController;
import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.exeption.DAOException;
import com.nextvoyager.conferences.dao.report.ReportDAOJDBC;
import com.nextvoyager.conferences.model.Event;
import com.nextvoyager.conferences.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nextvoyager.conferences.dao.DAOUtil.prepareStatement;
import static com.nextvoyager.conferences.dao.DAOUtil.toSqlDate;

public class EventDAOJDBC implements EventDAO{

    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT e.id, e.name, e.place, e.begin_date, e.end_date, e.participants_came FROM event AS e " +
                    "WHERE e.id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO event (name, place, begin_date, end_date, participants_came) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE event SET name = ?, place = ?, begin_date = ?, end_date = ?, participants_came = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM event WHERE id = ?";
    private static final String SQL_LIST =
            "SELECT e.id, e.name, e.place, e.begin_date, e.end_date, e.participants_came, report.r_count, participant.p_count " +
                    "FROM event AS e " +
                    "LEFT JOIN (SELECT COUNT(*) as r_count, event_id FROM report GROUP BY event_id) AS report ON e.id = report.event_id " +
                    "LEFT JOIN (SELECT COUNT(*) as p_count, event_id FROM event_has_participant GROUP BY event_id) AS participant ON e.id = participant.event_id ";
    private static final String SQL_LIST_ORDER_BY_BEGIN_DATE = SQL_LIST + "ORDER BY e.begin_date ";
    private static final String SQL_LIST_ORDER_BY_REPORTS = SQL_LIST + "ORDER BY r_count ";
    private static final String SQL_LIST_ORDER_BY_PARTICIPANTS = SQL_LIST + "ORDER BY p_count ";
    private static final String SQL_LIST_EVENT_REPORTS =
            "SELECT r.id, r.topic, r.speaker_id, r.event_id, r.report_status_id, s.name AS report_status_name, e.name AS event_name FROM report AS r " +
                    "LEFT JOIN report_status AS s ON r.report_status_id = s.id " +
                    "LEFT JOIN event AS e ON r.event_id = e.id " +
                    "WHERE r.event_id = ? " +
                    "ORDER BY r.id";
    private static final String SQL_LIST_COUNT_ALL = "SELECT COUNT(*) AS count_all FROM event";


    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Event DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     *
     * @param daoFactory The DAOFactory to construct this Event DAO for.
     */
    public EventDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Event find(Integer id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    /**
     * Returns the event from the database matching the given SQL query with the given values.
     *
     * @param sql    The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The event from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private Event find(String sql, Object... values) throws DAOException {
        Event event = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                event = map(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        if (event != null) {
            try (
                    Connection connection = daoFactory.getConnection();
                    PreparedStatement statement = prepareStatement(connection, SQL_LIST_EVENT_REPORTS, false, values);
                    ResultSet resultSet = statement.executeQuery();
            ) {
                List<Report> reportsList = new ArrayList<>();
                while (resultSet.next()) {
                    reportsList.add(ReportDAOJDBC.map(resultSet));
                }
                event.setReports(reportsList);
            } catch (SQLException | ClassNotFoundException e) {
                throw new DAOException(e);
            }
        }

        return event;
    }

    @Override
    public List<Event> list(OrderType orderType) throws DAOException {
        List<Event> events = new ArrayList<>();

        String currentSQL = SQL_LIST_ORDER_BY_BEGIN_DATE;
        switch (orderType) {
            case ReportsCount:
                currentSQL = SQL_LIST_ORDER_BY_REPORTS;
                break;
            case ParticipantsCount:
                currentSQL = SQL_LIST_ORDER_BY_PARTICIPANTS;
                break;
        }

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(currentSQL);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                events.add(map(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return events;
    }

    @Override
    public Map<Integer, List<Event>> listWithPagination(OrderType orderType, Integer limit, Integer offset) throws DAOException {
        Map<Integer, List<Event>> countAndList = new HashMap<>();
        List<Event> events = new ArrayList<>();

        String currentSQL = SQL_LIST_ORDER_BY_BEGIN_DATE;
        switch (orderType) {
            case ReportsCount:
                currentSQL = SQL_LIST_ORDER_BY_REPORTS;
                break;
            case ParticipantsCount:
                currentSQL = SQL_LIST_ORDER_BY_PARTICIPANTS;
                break;
        }

        Object[] valuesPagination = {limit,offset};
        currentSQL += "LIMIT ?, ? ";

        try (
                Connection connection = daoFactory.getConnection();
                Statement stmtCount = connection.createStatement();
                ResultSet resultSetCountAll = stmtCount.executeQuery(SQL_LIST_COUNT_ALL);
                PreparedStatement statementList = prepareStatement(connection, currentSQL, false, valuesPagination);
                ResultSet resultSetList = statementList.executeQuery();
        ) {
            while (resultSetList.next()) {
                events.add(map(resultSetList));
            }
            if (resultSetCountAll.first()) {
                countAndList.put(resultSetCountAll.getInt("count_all"), events);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return countAndList;
    }

    @Override
    public void create(Event event) throws IllegalArgumentException, DAOException {
        if (event.getId() != null) {
            throw new IllegalArgumentException("Event is already created, the event ID is not null.");
        }

        Object[] values = {
                event.getName(),
                event.getPlace(),
                toSqlDate(event.getBeginDate()),
                toSqlDate(event.getEndDate()),
                event.getParticipantsCame()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating event failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    event.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating event failed, no generated key obtained.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Event event) throws DAOException {
        if (event.getId() == null) {
            throw new IllegalArgumentException("Event is not created yet, the event ID is null.");
        }

        Object[] values = {
                event.getName(),
                event.getPlace(),
                toSqlDate(event.getBeginDate()),
                toSqlDate(event.getEndDate()),
                event.getParticipantsCame(),
                event.getId()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating event failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Event event) throws DAOException {
        Object[] values = {
                event.getId()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting event failed, no rows affected.");
            } else {
                event.setId(null);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to an Event.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped to an Event.
     * @return The mapped Event from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Event map(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        event.setName(resultSet.getString("name"));
        event.setPlace(resultSet.getString("place"));
        event.setBeginDate(resultSet.getDate("begin_date"));
        event.setEndDate(resultSet.getDate("end_date"));
        event.setParticipantsCame(resultSet.getInt("participants_came"));
        event.setReportsCount(resultSet.getInt("r_count"));
        event.setParticipantsCount(resultSet.getInt("p_count"));
        return event;
    }



}
