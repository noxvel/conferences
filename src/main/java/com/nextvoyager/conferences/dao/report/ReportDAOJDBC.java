package com.nextvoyager.conferences.dao.report;

import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.Event;
import com.nextvoyager.conferences.model.Report;
import com.nextvoyager.conferences.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.nextvoyager.conferences.dao.DAOUtil.prepareStatement;

public class ReportDAOJDBC implements ReportDAO {

    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT r.id, r.topic, r.speaker_id, r.event_id, r.report_status_id, r.description, " +
                            "s.name AS report_status_name, e.name AS event_name FROM report AS r " +
                    "LEFT JOIN report_status AS s ON r.report_status_id = s.id " +
                    "LEFT JOIN event AS e ON r.event_id = e.id " +
                    "WHERE r.id = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT r.id, r.topic, r.speaker_id, r.event_id, r.report_status_id, r.description, " +
                            "s.name AS report_status_name, e.name AS event_name FROM report AS r " +
                    "LEFT JOIN report_status AS s ON r.report_status_id = s.id " +
                    "LEFT JOIN event AS e ON r.event_id = e.id " +
                    "ORDER BY r.id";
    private static final String SQL_INSERT =
            "INSERT INTO report (topic, speaker_id, event_id, report_status_id, description) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE report SET topic = ?, speaker_id = ?, event_id = ?, report_status_id = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM report WHERE id = ?";


    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Report DAO for the given DAOFactory. Package private so that it can be constructed
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
        return find(SQL_FIND_BY_ID, id);
    }

    /**
     * Returns the report from the database matching the given SQL query with the given values.
     *
     * @param sql    The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The report from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private Report find(String sql, Object... values) throws DAOException {
        Report report = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
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
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
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
    public void create(Report report) throws IllegalArgumentException, DAOException {
        if (report.getId() != null) {
            throw new IllegalArgumentException("Report is already created, the report ID is not null.");
        }

        Object[] values = {
                report.getTopic(),
                report.getSpeaker().getId(),
                report.getEvent().getId(),
                report.getStatus().getId(),
                report.getDescription()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
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

        Object[] values = {
                report.getTopic(),
                report.getSpeaker().getId(),
                report.getEvent().getId(),
                report.getStatus().getId(),
                report.getDescription(),
                report.getId()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
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
        Object[] values = {
                report.getId()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
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
     * @param resultSet The ResultSet of which the current row is to be mapped to an Report.
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
