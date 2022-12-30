package com.nextvoyager.conferences.model.dao.report;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.utils.querybuilder.SelectQueryBuilder;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.nextvoyager.conferences.model.dao.utils.DAOUtil.prepareStatement;
import static com.nextvoyager.conferences.model.dao.utils.DAOUtil.ValueDAO;

public class ReportDAOMySQL implements ReportDAO {

    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT r.id, r.topic, r.speaker_id, r.event_id, r.report_status_id, r.description, " +
                            "s.name AS report_status_name, e.name AS event_name FROM report AS r " +
                    "LEFT JOIN report_status AS s ON r.report_status_id = s.id " +
                    "LEFT JOIN event AS e ON r.event_id = e.id " +
                    "WHERE r.id = ? ";
    private static final String SQL_LIST =
            "SELECT r.id, r.topic, r.speaker_id, r.event_id, r.report_status_id, r.description, " +
                            "s.name AS report_status_name, e.name AS event_name FROM report AS r " +
                    "LEFT JOIN report_status AS s ON r.report_status_id = s.id " +
                    "LEFT JOIN event AS e ON r.event_id = e.id ";
    private static final String SQL_ADD_WHERE_EVENT = "r.event_id = ? ";
    private static final String SQL_ADD_WHERE_SPEAKER = "r.speaker_id = ? ";
    private static final String SQL_ADD_WHERE_STATUS = "r.report_status_id = ? ";
    private static final String SQL_ADD_WHERE_SPEAKER_EVENT_VIEW = "(r.speaker_id = ? OR r.report_status_id IN (?,?)) ";
    private static final String SQL_INSERT =
            "INSERT INTO report (topic, speaker_id, event_id, report_status_id, description) VALUES (?, ?, ?, ?, ?) ";
    private static final String SQL_UPDATE =
            "UPDATE report SET topic = ?, speaker_id = ?, event_id = ?, report_status_id = ?, description = ? WHERE id = ? ";
    private static final String SQL_DELETE =
            "DELETE FROM report WHERE id = ? ";
    private static final String SQL_LIST_LIMIT = "LIMIT ?, ? ";
    private static final String SQL_LIST_COUNT_ALL = "SELECT COUNT(*) AS count_all FROM report AS r ";
    private static final String SQL_STATUS_UPDATE =
            "UPDATE report SET report_status_id = ? WHERE id = ? ";


    // Vars ---------------------------------------------------------------------------------------

    private final DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct a Report DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     *
     * @param daoFactory The DAOFactory to construct this Report DAO for.
     */
    public ReportDAOMySQL(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Report find(Integer id) throws DAOException {
        return find(SQL_FIND_BY_ID, new ValueDAO(id,Types.INTEGER));
    }

    /**
     * Returns the report from the database matching the given SQL query with the given values.
     *
     * @param sql    The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The report from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private Report find(String sql, ValueDAO... values) throws DAOException {
        Report report = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                report = map(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return report;
    }

    @Override
    public List<Report> list() throws DAOException {
        List<Report> reports = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_LIST, false);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                reports.add(map(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return reports;
    }

    @Override
    public List<Report> list(Integer eventID) throws DAOException {
        List<Report> reports = new ArrayList<>();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .build();
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, currentSQL, false, new ValueDAO(eventID, Types.INTEGER));
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                reports.add(map(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return reports;
    }

    @Override
    public ListWithCountResult listWithPagination(Integer page, Integer limit) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {};

        ValueDAO[] valuesPagination = {
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(int page, int limit, Integer eventID, User speaker) {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setFilter(SQL_ADD_WHERE_SPEAKER_EVENT_VIEW, true)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setFilter(SQL_ADD_WHERE_SPEAKER_EVENT_VIEW, true)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(Report.Status.FREE.getId(), Types.INTEGER),
                new ValueDAO(Report.Status.CONFIRMED.getId(), Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(Report.Status.FREE.getId(), Types.INTEGER),
                new ValueDAO(Report.Status.CONFIRMED.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(int page, int limit, Integer eventID, User speaker, Report.Status status) {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setFilter(SQL_ADD_WHERE_SPEAKER, true)
                .setFilter(SQL_ADD_WHERE_STATUS, true)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setFilter(SQL_ADD_WHERE_SPEAKER, true)
                .setFilter(SQL_ADD_WHERE_STATUS, true)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(status.getId(), Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(status.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(int page, int limit, Report.Status status) {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_STATUS)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_STATUS)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(status.getId(), Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(status.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(Integer page, Integer limit, User speaker) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_SPEAKER)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_SPEAKER)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(speaker.getId(), Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(Integer page, Integer limit, User speaker, Report.Status status) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_SPEAKER)
                .setFilter(SQL_ADD_WHERE_STATUS, true)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_SPEAKER)
                .setFilter(SQL_ADD_WHERE_STATUS, true)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(status.getId(), Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(speaker.getId(), Types.INTEGER),
                new ValueDAO(status.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(Integer page, Integer limit, Integer eventID) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(eventID, Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    @Override
    public ListWithCountResult listWithPagination(Integer page, Integer limit, Integer eventID, Report.Status status) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        String currentAllCount = new SelectQueryBuilder(SQL_LIST_COUNT_ALL)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setFilter(SQL_ADD_WHERE_STATUS, true)
                .build();

        String currentSQL = new SelectQueryBuilder(SQL_LIST)
                .setFilter(SQL_ADD_WHERE_EVENT)
                .setFilter(SQL_ADD_WHERE_STATUS, true)
                .setLimit(SQL_LIST_LIMIT)
                .build();

        ValueDAO[] valuesAllCount = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(status.getId(), Types.INTEGER)
        };

        ValueDAO[] valuesPagination = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(status.getId(), Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        return listWithPagination(currentAllCount, currentSQL, valuesAllCount, valuesPagination);
    }

    public ListWithCountResult listWithPagination(String sqlAllCount, String sql, ValueDAO[] valuesAllCount,
                                                  ValueDAO[] values) throws DAOException {
        ListWithCountResult result = new ListWithCountResult();
        List<Report> reports = new ArrayList<>();
        result.setList(reports);

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement stmtCount = prepareStatement(connection, sqlAllCount, false, valuesAllCount);
                ResultSet resultSetCountAll = stmtCount.executeQuery();
                PreparedStatement statementList = prepareStatement(connection, sql, false, values);
                ResultSet resultSetList = statementList.executeQuery()
        ) {
            if (resultSetCountAll.next()) {
                result.setCount(resultSetCountAll.getInt("count_all"));
                while (resultSetList.next()) {
                    reports.add(map(resultSetList));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return result;
    }


    @Override
    public void create(Report report) throws IllegalArgumentException, DAOException {
        if (report.getId() != null) {
            throw new IllegalArgumentException("Report is already created, the report ID is not null.");
        }

        // if speaker field is null
        ValueDAO speakerValue = new ValueDAO(null, Types.INTEGER);
        if (report.getSpeaker() != null) {
            speakerValue.setValue(report.getSpeaker().getId());
        }

        ValueDAO[] values = {
                new ValueDAO(report.getTopic(),Types.VARCHAR),
                speakerValue,
                new ValueDAO(report.getEvent().getId(),Types.INTEGER),
                new ValueDAO(report.getStatus().getId(),Types.INTEGER),
                new ValueDAO(report.getDescription(),Types.VARCHAR)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating report failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    report.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating report failed, no generated key obtained.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Report report) throws DAOException {
        if (report.getId() == null) {
            throw new IllegalArgumentException("Report is not created yet, the report ID is null.");
        }

        // if speaker field is null
        ValueDAO speakerValue = new ValueDAO(null, Types.INTEGER);
        if (report.getSpeaker() != null) {
            speakerValue.setValue(report.getSpeaker().getId());
        }

        ValueDAO[] values = {
                new ValueDAO(report.getTopic(),Types.VARCHAR),
                speakerValue,
                new ValueDAO(report.getEvent().getId(),Types.INTEGER),
                new ValueDAO(report.getStatus().getId(),Types.INTEGER),
                new ValueDAO(report.getDescription(),Types.VARCHAR),
                new ValueDAO(report.getId(),Types.INTEGER),
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating report failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Report report) throws DAOException {
        ValueDAO[] values = {
                new ValueDAO(report.getId(),Types.INTEGER),
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting report failed, no rows affected.");
            } else {
                report.setId(null);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to a Report.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped to a Report.
     * @return The mapped Report from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    public static Report map(ResultSet resultSet) throws SQLException {
        Report report = new Report();
        report.setId(resultSet.getInt("id"));
        report.setTopic(resultSet.getString("topic"));
        int speakerID = resultSet.getInt("speaker_id");
        report.setSpeaker(speakerID != 0 ? new User(speakerID) : null);
        report.setEvent(new Event(resultSet.getInt("event_id"),resultSet.getString("event_name")));
        report.setStatus(Report.Status.valueOf(resultSet.getString("report_status_name")));
        report.setDescription(resultSet.getString("description"));
        return report;
    }


}
