package com.nextvoyager.conferences.model.dao.user;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.ValueDAO;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.util.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.nextvoyager.conferences.model.dao.utils.DAOUtil.*;

/**
 * Implementation of UserDAO for MySQL database
 *
 * @author Stanislav Bozhevskyi
 */
public class UserDAOMySQL implements UserDAO{

    // Constants ----------------------------------------------------------------------------------

    public static final String FIELD_ID = "id";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_FIRST_NAME = "first_name";
    public static final String FIELD_LAST_NAME = "last_name";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_USER_ROLE_NAME = "user_role_name";
    public static final String FIELD_RECEIVE_NOTIFICATIONS = "receive_notifications";
    public static final String FIELD_COUNT_ALL = "count_all";

    private static final String SQL_USER_FOR_LOGIN =
            "SELECT u.id, u.email, u.password FROM user AS u WHERE u.email = ?";

    private static final String SQL_USER_SELECT =
            "SELECT u.id, u.email, u.first_name, u.last_name, u.user_role_id, u.receive_notifications, r.name AS user_role_name FROM user AS u " +
                    "LEFT JOIN user_role AS r ON u.user_role_id = r.id ";
    private static final String SQL_FIND_BY_ID = SQL_USER_SELECT +
                    "WHERE u.id = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD = SQL_USER_SELECT +
                    "WHERE email = ? AND password = ?";
    private static final String SQL_LIST_ORDER_BY_ID = SQL_USER_SELECT +
                    "ORDER BY u.id " +
                    "LIMIT ?, ?";
    private static final String SQL_LIST_WITH_ONLY_ONE_ROLE = SQL_USER_SELECT +
                    "WHERE u.user_role_id = ? " +
                    "ORDER BY u.id";
    private static final String SQL_LIST_EVENT_PARTICIPANTS = SQL_USER_SELECT +
                    "INNER JOIN event_has_participant AS ehp ON ehp.user_id = u.id " +
                    "WHERE ehp.event_id = ? ";

    private static final String SQL_INSERT =
            "INSERT INTO user (email, password, first_name, last_name, user_role_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE user SET email = ?, first_name = ?, last_name = ?, user_role_id = ?, receive_notifications = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ?";
    private static final String SQL_EXIST_EMAIL = "SELECT id FROM user WHERE email = ?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private static final String SQL_CHECK_PASSWORD = "SELECT id, password FROM user WHERE id = ?";
    private static final String SQL_LIST_WHO_RECEIVE_NOTIFICATIONS =
        "SELECT DISTINCT u.id, u.email, u.first_name, u.last_name, u.user_role_id, u.receive_notifications, r.name AS user_role_name FROM user AS u " +
            "LEFT JOIN user_role AS r ON u.user_role_id = r.id " +
            "WHERE u.receive_notifications " +
            "AND (EXISTS (SELECT id FROM report WHERE report.event_id = ? AND report.speaker_id = u.id) " +
            "OR " +
            "EXISTS (SELECT ehp.user_id FROM event_has_participant AS ehp WHERE ehp.event_id = ? AND ehp.user_id = u.id)) " +
            "ORDER BY u.id";
    private static final String SQL_LIST_COUNT_ALL = "SELECT COUNT(*) AS count_all FROM user AS u ";

    // Vars ---------------------------------------------------------------------------------------

    private final DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct a User DAO for the given DAOFactory.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    public UserDAOMySQL(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public User find(Integer id) throws DAOException {
        return find(SQL_FIND_BY_ID, new ValueDAO(id, Types.INTEGER));
    }

//    @Override
//    public User find(String email, String password) throws DAOException {
//        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, new ValueDAO(email,Types.VARCHAR), new ValueDAO(password, Types.VARCHAR));
//    }

    @Override
    public User find(String email, String password) throws DAOException {
        User user = null;
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_USER_FOR_LOGIN,
                        false, new ValueDAO(email, Types.VARCHAR));
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                if (PasswordEncoder.check(password, resultSet.getString(FIELD_PASSWORD))) {
                    user = find(SQL_FIND_BY_ID, new ValueDAO(resultSet.getInt(FIELD_ID), Types.INTEGER));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return user;
    }

    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The ValueDAO values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private User find(String sql, ValueDAO... values) throws DAOException {
        User user;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            user = processUserRS(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return user;
    }

    @Override
    public void create(User user) throws IllegalArgumentException, DAOException {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        ValueDAO[] values = {
                new ValueDAO(user.getEmail(), Types.VARCHAR),
                new ValueDAO(PasswordEncoder.hash(user.getPassword()),Types.VARCHAR),
                new ValueDAO(user.getFirstName(),Types.VARCHAR),
                new ValueDAO(user.getLastName(),Types.VARCHAR),
                new ValueDAO(user.getRole().getId(),Types.INTEGER),
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        ValueDAO[] values = {
                new ValueDAO(user.getEmail(), Types.VARCHAR),
                new ValueDAO(user.getFirstName(), Types.VARCHAR),
                new ValueDAO(user.getLastName(), Types.VARCHAR),
                new ValueDAO(user.getRole().getId(), Types.INTEGER),
                new ValueDAO(user.getReceiveNotifications(), Types.BOOLEAN),
                new ValueDAO(user.getId(), Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        ValueDAO[] values = {
                new ValueDAO(user.getId(),Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                user.setId(null);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ListWithCount<User> list(int page, int limit) throws DAOException {
        int offset;
        offset = (page - 1) * limit;

        ValueDAO[] values = {
                new ValueDAO(offset, Types.INTEGER),
                new ValueDAO(limit, Types.INTEGER)
        };

        ListWithCount<User> result = new ListWithCount<>();
        result.setList(new ArrayList<>());

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement stmtCount = prepareStatement(connection, SQL_LIST_COUNT_ALL, false);
                ResultSet resultSetCountAll = stmtCount.executeQuery();
                PreparedStatement statementList = prepareStatement(connection,SQL_LIST_ORDER_BY_ID, false, values);
                ResultSet resultSetList = statementList.executeQuery()
        ) {
            processUserListRS(resultSetCountAll,resultSetList,result);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage(),e);
        }

        return result;
    }

    @Override
    public List<User> listWithOneRole(User.Role userRole) throws DAOException {
        ValueDAO[] values = {
                new ValueDAO(userRole.getId(), Types.INTEGER)
        };
        return list(SQL_LIST_WITH_ONLY_ONE_ROLE, values);

    }

    @Override
    public List<User> listOfEventParticipants(Event event) {
        ValueDAO[] values = {
                new ValueDAO(event.getId(), Types.INTEGER)
        };
        return list(SQL_LIST_EVENT_PARTICIPANTS, values);
    }

    @Override
    public List<User> receiveEventNotificationsList(Event event) {
        ValueDAO[] values = {
                new ValueDAO(event.getId(), Types.INTEGER),
                new ValueDAO(event.getId(), Types.INTEGER)
        };
        return list(SQL_LIST_WHO_RECEIVE_NOTIFICATIONS, values);
    }

    private List<User> list(String sql, ValueDAO... values) {
        List<User> users = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            processUserListRS(resultSet,users);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return users;
    }
    @Override
    public boolean existEmail(String email) throws DAOException {
        ValueDAO[] values = {
                new ValueDAO(email,Types.VARCHAR)
        };

        boolean exist;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_EMAIL, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            exist = resultSet.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return exist;
    }

    @Override
    public void changePassword(User user) throws DAOException, IllegalArgumentException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        ValueDAO[] values = {
                new ValueDAO(PasswordEncoder.hash(user.getPassword()),Types.VARCHAR),
                new ValueDAO(user.getId(),Types.VARCHAR)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_CHANGE_PASSWORD, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing password failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public boolean checkPassword(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        boolean match = false;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_CHECK_PASSWORD,
                        false, new ValueDAO(user.getId(),Types.VARCHAR));
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                if (PasswordEncoder.check(user.getPassword(), resultSet.getString(FIELD_PASSWORD))) {
                    match = true;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return match;
    }

    // Helpers ------------------------------------------------------------------------------------

    protected User processUserRS(ResultSet rs) throws SQLException {
        return processRS(rs, this::map);
    }

    protected void processUserListRS(ResultSet countRS, ResultSet listRS, ListWithCount<User> result) throws SQLException {
        processListRS(countRS,listRS,result,FIELD_COUNT_ALL, this::map);
    }

    protected void processUserListRS(ResultSet listRS, List<User> result) throws SQLException {
        processListRS(listRS,result,this::map);
    }
    /**
     * Map the current row of the given ResultSet to a User.
     * @param resultSet The ResultSet of which the current row is to be mapped to a User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(FIELD_ID));
        user.setEmail(resultSet.getString(FIELD_EMAIL));
        user.setFirstName(resultSet.getString(FIELD_FIRST_NAME));
        user.setLastName(resultSet.getString(FIELD_LAST_NAME));
        user.setRole(User.Role.valueOf(resultSet.getString(FIELD_USER_ROLE_NAME)));
        user.setReceiveNotifications(resultSet.getBoolean(FIELD_RECEIVE_NOTIFICATIONS));
        return user;
    }
}
