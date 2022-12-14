package com.nextvoyager.conferences.model.dao.report;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.nextvoyager.conferences.model.dao.DAOUtil.prepareStatement;
import static com.nextvoyager.conferences.model.dao.DAOUtil.ValueDAO;

public class ReportDAOJDBC implements ReportDAO {

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
                    "LEFT JOIN event AS e ON r.event_id = e.id " +
                    "WHERE r.event_id = ? ";
    private static final String SQL_INSERT =
            "INSERT INTO report (topic, speaker_id, event_id, report_status_id, description) VALUES (?, ?, ?, ?, ?) ";
    private static final String SQL_UPDATE =
            "UPDATE report SET topic = ?, speaker_id = ?, event_id = ?, report_status_id = ?, description = ? WHERE id = ? ";
    private static final String SQL_DELETE =
            "DELETE FROM report WHERE id = ? ";
    private static final String SQL_LIST_LIMIT = "LIMIT ?, ? ";
    private static final String SQL_LIST_COUNT_ALL = "SELECT COUNT(*) AS count_all FROM report AS r WHERE r.event_id = ? ";
    private static final String SQL_LIST_ADD_FILTER_STATUS = " AND r.report_status_id = ? ";


    // Vars ---------------------------------------------------------------------------------------

    private final DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct a Report DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     *
     * @param daoFactory The DAOFactory to construct this Report DAO for.
     */
    public ReportDAOJDBC(DAOFactory daoFactory) {
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
    public List<Report> list(Integer eventID) throws DAOException {
        List<Report> reports = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_LIST, false, new ValueDAO(eventID, Types.INTEGER));
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
    public ListWithCountResult listWithPagination(Integer eventID, Integer page, Integer limit) throws DAOException {
        ListWithCountResult result = new ListWithCountResult();
        List<Report> reports = new ArrayList<>();
        result.setList(reports);

        String currentSQL = SQL_LIST;
        currentSQL += SQL_LIST_LIMIT;

        int offset;
        offset = (page - 1) * limit;

        ValueDAO[] valuesPagination = {
                new ValueDAO(eventID, Types.INTEGER),
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement stmtCount = prepareStatement(connection, SQL_LIST_COUNT_ALL, false, new ValueDAO(eventID,Types.INTEGER));
                ResultSet resultSetCountAll = stmtCount.executeQuery();
                PreparedStatement statementList = prepareStatement(connection, currentSQL, false, valuesPagination);
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
    public ListWithCountResult listWithPagination(Integer eventID, int page, int limit, Report.Status status) throws DAOException {
        ListWithCountResult result = new ListWithCountResult();
        List<Report> reports = new ArrayList<>();
        result.setList(reports);

        String currentAllCount = SQL_LIST_COUNT_ALL;
        currentAllCount += SQL_LIST_ADD_FILTER_STATUS;

        String currentSQL = SQL_LIST;
        currentSQL += SQL_LIST_ADD_FILTER_STATUS;
        currentSQL += SQL_LIST_LIMIT;

        int offset;
        offset = (page - 1) * limit;

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

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement stmtCount = prepareStatement(connection, currentAllCount, false, valuesAllCount);
                ResultSet resultSetCountAll = stmtCount.executeQuery();
                PreparedStatement statementList = prepareStatement(connection, currentSQL, false, valuesPagination);
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

        ValueDAO[] values = {
                new ValueDAO(report.getTopic(),Types.VARCHAR),
                new ValueDAO(report.getSpeaker().getId(),Types.INTEGER),
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
        report.setSpeaker(new User(resultSet.getInt("speaker_id")));
        report.setEvent(new Event(resultSet.getInt("event_id"),resultSet.getString("event_name")));
        report.setStatus(Report.Status.valueOf(resultSet.getString("report_status_name")));
        report.setDescription(resultSet.getString("description"));
        return report;
    }


}
