package com.nextvoyager.conferences.model.dao.event;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.ValueDAO;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.dao.utils.querybuilder.SelectQueryBuilder;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.nextvoyager.conferences.model.dao.utils.DAOUtil.*;

public class EventDAOMySQL implements EventDAO{

    // Constants ----------------------------------------------------------------------------------

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_PLACE = "place";
    private static final String FIELD_BEGIN_DATE = "begin_date";
    private static final String FIELD_END_DATE = "end_date";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PARTICIPANTS_CAME = "participants_came";
    private static final String FIELD_REPORT_COUNT = "r_count";
    private static final String FIELD_PARTICIPANTS_COUNT = "p_count";
    private static final String FIELD_COUNT_ALL = "count_all";

    private static final String SQL_FIND_BY_ID =
            "SELECT e.id, e.name, e.place, e.begin_date, e.end_date, e.participants_came, e.description FROM event AS e " +
                    "WHERE e.id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO event (name, place, begin_date, end_date, participants_came, description) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE event SET name = ?, place = ?, begin_date = ?, end_date = ?, participants_came = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM event WHERE id = ?";

    private static final String SQL_LIST_BASE =
            "SELECT e.id, e.name, e.place, e.begin_date, e.end_date, e.participants_came, e.description, report.r_count, participant.p_count " +
                    "FROM event AS e " +
                    "LEFT JOIN (SELECT COUNT(*) as p_count, event_id FROM event_has_participant GROUP BY event_id) AS participant ON e.id = participant.event_id ";
    private static final String SQL_LIST = SQL_LIST_BASE +
            "LEFT JOIN (SELECT COUNT(*) as r_count, event_id FROM report " +
            "GROUP BY event_id) AS report ON e.id = report.event_id ";
    private static final String SQL_LIST_REPORT_COUNT_REPORT_STATUS = SQL_LIST_BASE +
            "LEFT JOIN (SELECT COUNT(*) as r_count, event_id FROM report WHERE report_status_id = ? " +
            "GROUP BY event_id) AS report ON e.id = report.event_id ";
    private static final String SQL_LIST_REPORT_COUNT_FOR_SPEAKER = SQL_LIST_BASE +
            "LEFT JOIN (SELECT COUNT(*) as r_count, event_id FROM report AS r WHERE (r.speaker_id = ? OR r.report_status_id IN (?,?)) " +
            "GROUP BY event_id) AS report ON e.id = report.event_id ";

    private static final String SQL_LIST_ORDER_BY_BEGIN_DATE = "ORDER BY e.begin_date ";
    private static final String SQL_LIST_ORDER_BY_REPORTS = "ORDER BY r_count ";
    private static final String SQL_LIST_ORDER_BY_PARTICIPANTS = "ORDER BY p_count ";

    private static final String SQL_LIST_ORDER_DIRECTION_ASC = "ASC ";
    private static final String SQL_LIST_ORDER_DIRECTION_DESC = "DESC ";

    private static final String SQL_LIST_WHERE_SPEAKER_PARTICIPATED = "EXISTS (SELECT id FROM report WHERE report.event_id = e.id AND report.speaker_id = ?) ";
    private static final String SQL_LIST_WHERE_ORDINARY_USER_PARTICIPATED = "EXISTS (SELECT ehp.event_id FROM event_has_participant AS ehp " +
                                                            "WHERE ehp.event_id = e.id AND ehp.user_id = ?) ";
    private static final String SQL_LIST_WHERE_TIME_FILTER_FUTURE = "e.end_date >= now() ";
    private static final String SQL_LIST_WHERE_TIME_FILTER_PAST = "e.end_date <= now() ";

    private static final String SQL_LIST_COUNT_ALL = "SELECT COUNT(*) AS count_all FROM event AS e ";
    private static final String SQL_REGISTER_USER_TO_EVENT = "INSERT INTO event_has_participant (event_id, user_id) VALUES (?, ?)" ;
    private static final String SQL_UNREGISTER_USER_TO_EVENT = "DELETE FROM event_has_participant WHERE event_id = ? AND user_id = ?";
    private static final String SQL_IS_USER_REGISTER = "SELECT * FROM event_has_participant WHERE event_id = ? AND user_id = ?";

    // Vars ---------------------------------------------------------------------------------------

    private final DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Event DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     *
     * @param daoFactory The DAOFactory to construct this Event DAO for.
     */
    public EventDAOMySQL(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Event find(Integer id) throws DAOException {
        return find(SQL_FIND_BY_ID, new ValueDAO(id, Types.INTEGER));
    }

    /**
     * Returns the event from the database matching the given SQL query with the given values.
     *
     * @param sql    The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The event from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private Event find(String sql, ValueDAO... values) throws DAOException {
        Event event;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            event = processEventRS(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return event;
    }

    @Override
    public void create(Event event) throws IllegalArgumentException, DAOException {
        if (event.getId() != null) {
            throw new IllegalArgumentException("Event is already created, the event ID is not null.");
        }

        ValueDAO[] values = {
                new ValueDAO(event.getName(), Types.VARCHAR),
                new ValueDAO(event.getPlace(), Types.VARCHAR),
                new ValueDAO(event.getBeginDate(), Types.TIMESTAMP),
                new ValueDAO(event.getEndDate(), Types.TIMESTAMP),
                new ValueDAO(event.getParticipantsCame(), Types.INTEGER),
                new ValueDAO(event.getDescription(), Types.VARCHAR)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values)
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

        ValueDAO[] values = {
                new ValueDAO(event.getName(), Types.VARCHAR),
                new ValueDAO(event.getPlace(), Types.VARCHAR),
                new ValueDAO(event.getBeginDate(), Types.TIMESTAMP),
                new ValueDAO(event.getEndDate(), Types.TIMESTAMP),
                new ValueDAO(event.getParticipantsCame(), Types.INTEGER),
                new ValueDAO(event.getDescription(), Types.VARCHAR),
                new ValueDAO(event.getId(), Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)
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
        ValueDAO[] values = {
                new ValueDAO(event.getId(), Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values)
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

    @Override
    public void registerUser(Integer eventID, User user, boolean register) {

        String currentSql = SQL_REGISTER_USER_TO_EVENT;
        if (!register) {
            currentSql = SQL_UNREGISTER_USER_TO_EVENT;
        }

        ValueDAO[] values = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(user.getId(), Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, currentSql, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting event failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean isUserRegisterEvent(Event event, User user) {
        boolean result = false;

        ValueDAO[] values = {
                new ValueDAO(event.getId(), Types.INTEGER),
                new ValueDAO(user.getId(), Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_IS_USER_REGISTER, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return result;
    }
    @Override
    public List<Event> list(SortType sortType, SortDirection sortDirection, TimeFilter timeFilter) throws DAOException {
        List<Event> events = new ArrayList<>();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(getTimeFilter(timeFilter))
                .setSortType(getSortType(sortType))
                .setSortDirection(getSortDirection(sortDirection))
                .build();

        ValueDAO[] values = {};

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, currentSQL, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            processEventListRS(resultSet, events);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return events;
    }


    @Override
    public ListWithCount<Event> listWithPagination(Integer page, Integer limit, SortType sortType,
                                                  SortDirection sortDirection, TimeFilter timeFilter) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(getTimeFilter(timeFilter))
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(getTimeFilter(timeFilter))
                .setSortType(getSortType(sortType))
                .setSortDirection(getSortDirection(sortDirection))
                .setLimit()
                .build();

        ValueDAO[] valuesAllCount = {};

        ValueDAO[] valuesPagination = {
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCount<Event> listWithPaginationReportStatusFilter(int page, int limit, SortType sortType,
                                                                    SortDirection sortDirection, TimeFilter timeFilter,
                                                                    Report.Status status) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(getTimeFilter(timeFilter))
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST_REPORT_COUNT_REPORT_STATUS)
                .setFilter(getTimeFilter(timeFilter))
                .setSortType(getSortType(sortType))
                .setSortDirection(getSortDirection(sortDirection))
                .setLimit()
                .build();

        ValueDAO[] valuesAllCount = {};

        ValueDAO[] valuesPagination = {
                new ValueDAO(status.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }


    @Override
    public ListWithCount<Event> listWithPaginationSpeaker(int page, int limit, SortType sortType,
                                                         SortDirection sortDirection, TimeFilter timeFilter,
                                                         User speaker, Boolean participated) {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(getTimeFilter(timeFilter))
                .setFilter(participated ? SQL_LIST_WHERE_SPEAKER_PARTICIPATED : null)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST_REPORT_COUNT_FOR_SPEAKER)
                .setFilter(getTimeFilter(timeFilter))
                .setFilter(participated ? SQL_LIST_WHERE_SPEAKER_PARTICIPATED : null)
                .setSortType(getSortType(sortType))
                .setSortDirection(getSortDirection(sortDirection))
                .setLimit()
                .build();

        ValueDAO[] valuesAllCount = {
                participated ? new ValueDAO(speaker.getId(), Types.INTEGER) : null,
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(Report.Status.FREE.getId(), Types.INTEGER),
                new ValueDAO(Report.Status.CONFIRMED.getId(), Types.INTEGER),
                participated ? new ValueDAO(speaker.getId(), Types.INTEGER) : null,
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);

    }

    @Override
    public ListWithCount<Event> listWithPaginationOrdinaryUser(int page, int limit, SortType sortType,
                                                         SortDirection sortDirection, TimeFilter timeFilter,
                                                         User ordinaryUser, Boolean participated) {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(getTimeFilter(timeFilter))
                .setFilter(participated ? SQL_LIST_WHERE_ORDINARY_USER_PARTICIPATED : null)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST_REPORT_COUNT_REPORT_STATUS)
                .setFilter(getTimeFilter(timeFilter) )
                .setFilter(participated ? SQL_LIST_WHERE_ORDINARY_USER_PARTICIPATED : null)
                .setSortType(getSortType(sortType))
                .setSortDirection(getSortDirection(sortDirection))
                .setLimit()
                .build();

        ValueDAO[] valuesAllCount = {
                participated ? new ValueDAO(ordinaryUser.getId(), Types.INTEGER) : null,
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(Report.Status.CONFIRMED.getId(), Types.INTEGER),
                participated ? new ValueDAO(ordinaryUser.getId(), Types.INTEGER) : null,
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    private ListWithCount<Event> listWithPagination(String sqlAllCount, String sql, ValueDAO[] valuesAllCount,
                                                             ValueDAO[] values) throws DAOException {
        ListWithCount<Event> result = new ListWithCount<>();
        result.setList(new ArrayList<>());

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement stmtCount = prepareStatement(connection, sqlAllCount, false, valuesAllCount);
                ResultSet resultSetCountAll = stmtCount.executeQuery();
                PreparedStatement statementList = prepareStatement(connection, sql, false, values);
                ResultSet resultSetList = statementList.executeQuery()
        ) {
            processEventListRS(resultSetCountAll, resultSetList, result);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return result;
    }

    // Helpers -----------------------------------------------------------------------------------

    public Event processEventRS(ResultSet rs) throws SQLException {
        return processRS(rs, this::map);
    }

    public void processEventListRS(ResultSet countRS, ResultSet listRS, ListWithCount<Event> result) throws SQLException {
        processListRS(countRS,listRS,result,FIELD_COUNT_ALL, this::mapForList);
    }

    public void processEventListRS(ResultSet listRS, List<Event> result) throws SQLException {
        processListRS(listRS,result,this::mapForList);
    }

    /**
     * Map the current row of the given ResultSet to an Event.
     *
     * @param eventRS The ResultSet of which the current row is to be mapped to an Event.
     * @return The mapped Event from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private Event map(ResultSet eventRS) throws SQLException{
        return new Event.EventBuilder()
                .setId(eventRS.getInt(FIELD_ID))
                .setName(eventRS.getString(FIELD_NAME))
                .setPlace(eventRS.getString(FIELD_PLACE))
                .setBeginDate(eventRS.getTimestamp(FIELD_BEGIN_DATE).toLocalDateTime())
                .setEndDate(eventRS.getTimestamp(FIELD_END_DATE).toLocalDateTime())
                .setDescription(eventRS.getString(FIELD_DESCRIPTION))
                .setParticipantsCame(eventRS.getInt(FIELD_PARTICIPANTS_CAME))
                .build();
    }

    private Event mapForList(ResultSet eventListRS) throws SQLException{
        return new Event.EventBuilder()
            .setId(eventListRS.getInt(FIELD_ID))
            .setName(eventListRS.getString(FIELD_NAME))
            .setPlace(eventListRS.getString(FIELD_PLACE))
            .setBeginDate(eventListRS.getTimestamp(FIELD_BEGIN_DATE).toLocalDateTime())
            .setEndDate(eventListRS.getTimestamp(FIELD_END_DATE).toLocalDateTime())
            .setDescription(eventListRS.getString(FIELD_DESCRIPTION))
            .setParticipantsCame(eventListRS.getInt(FIELD_PARTICIPANTS_CAME))
            .setReportsCount(eventListRS.getInt(FIELD_REPORT_COUNT))
            .setParticipantsCount(eventListRS.getInt(FIELD_PARTICIPANTS_COUNT))
            .build();
    }

    private static String getSortDirection(SortDirection sortDirection) {
        String result = "";
        switch (sortDirection) {
            case Ascending:
                result = SQL_LIST_ORDER_DIRECTION_ASC;
                break;
            case Descending:
                result = SQL_LIST_ORDER_DIRECTION_DESC;
                break;
        }
        return result;
    }

    private static String getSortType(SortType sortType) {
        String result = "";
        switch (sortType) {
            case Date:
                result = SQL_LIST_ORDER_BY_BEGIN_DATE;
                break;
            case ReportsCount:
                result = SQL_LIST_ORDER_BY_REPORTS;
                break;
            case ParticipantsCount:
                result = SQL_LIST_ORDER_BY_PARTICIPANTS;
                break;
        }
        return result;
    }

    private String getTimeFilter(TimeFilter timeFilter) {
        String result = null;
        switch (timeFilter) {
            case Future:
                result = SQL_LIST_WHERE_TIME_FILTER_FUTURE;
                break;
            case Past:
                result = SQL_LIST_WHERE_TIME_FILTER_PAST;
                break;
        }
        return result;
    }

}
