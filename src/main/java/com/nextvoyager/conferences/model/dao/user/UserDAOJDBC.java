package com.nextvoyager.conferences.model.dao.user;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.nextvoyager.conferences.model.dao.utils.DAOUtil.prepareStatement;
import static com.nextvoyager.conferences.model.dao.utils.DAOUtil.ValueDAO;

public class UserDAOJDBC implements UserDAO{

    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT u.id, u.email, u.first_name, u.last_name, u.user_role_id, r.name AS user_role_name FROM user AS u " +
                    "LEFT JOIN user_role AS r ON u.user_role_id = r.id " +
                    "WHERE u.id = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT u.id, u.email, u.first_name, u.last_name, u.user_role_id, r.name AS user_role_name FROM user AS u " +
                    "LEFT JOIN user_role AS r ON u.user_role_id = r.id " +
                    "WHERE email = ? AND password = MD5(?)";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT u.id, u.email, u.first_name, u.last_name, u.user_role_id, r.name AS user_role_name FROM user AS u " +
                    "LEFT JOIN user_role AS r ON u.user_role_id = r.id " +
                    "ORDER BY u.id";
    private static final String SQL_LIST_WITH_ONLY_ONE_ROLE =
            "SELECT u.id, u.email, u.first_name, u.last_name, u.user_role_id, r.name AS user_role_name FROM user AS u " +
                    "LEFT JOIN user_role AS r ON u.user_role_id = r.id " +
                    "WHERE u.user_role_id = ? " +
                    "ORDER BY u.id";
    private static final String SQL_INSERT =
            "INSERT INTO user (email, password, first_name, last_name, user_role_id) VALUES (?, MD5(?), ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE user SET email = ?, first_name = ?, last_name = ?, user_role_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ?";
    private static final String SQL_EXIST_EMAIL = "SELECT id FROM user WHERE email = ?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE user SET password = MD5(?) WHERE id = ?";
    private static final String SQL_CHECK_PASSWORD = "SELECT id FROM user WHERE id = ? AND password = MD5(?)";

    // Vars ---------------------------------------------------------------------------------------

    private final DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct a User DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    public UserDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public User find(Integer id) throws DAOException {
        return find(SQL_FIND_BY_ID, new ValueDAO(id, Types.INTEGER));
    }

    @Override
    public User find(String email, String password) throws DAOException {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, new ValueDAO(email,Types.VARCHAR), new ValueDAO(password, Types.VARCHAR));
    }

    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private User find(String sql, ValueDAO... values) throws DAOException {
        User user = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return user;
    }

    @Override
    public List<User> list() throws DAOException {
        List<User> users = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return users;
    }

    @Override
    public List<User> listWithOneRole(User.Role userRole) throws DAOException {
        List<User> users = new ArrayList<>();

        ValueDAO[] values = {
                new ValueDAO(userRole.getId(), Types.INTEGER)
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_LIST_WITH_ONLY_ONE_ROLE, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return users;
    }

    @Override
    public void create(User user) throws IllegalArgumentException, DAOException {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        ValueDAO[] values = {
                new ValueDAO(user.getEmail(), Types.VARCHAR),
                new ValueDAO(user.getPassword(),Types.VARCHAR),
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
                new ValueDAO(user.getFirstName(),Types.VARCHAR),
                new ValueDAO(user.getLastName(),Types.VARCHAR),
                new ValueDAO(user.getRole().getId(),Types.INTEGER),
                new ValueDAO(user.getId(),Types.INTEGER)
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
    public boolean existEmail(String email) throws DAOException {
        ValueDAO[] values = {
                new ValueDAO(email,Types.VARCHAR)
        };

        boolean exist = false;

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
    public void changePassword(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        ValueDAO[] values = {
                new ValueDAO(user.getPassword(),Types.VARCHAR),
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

        ValueDAO[] values = {
                new ValueDAO(user.getId(),Types.VARCHAR),
                new ValueDAO(user.getPassword(),Types.VARCHAR)
        };

        boolean exist = false;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_CHECK_PASSWORD, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            exist = resultSet.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return exist;
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to a User.
     * @param resultSet The ResultSet of which the current row is to be mapped to a User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setRole(User.Role.valueOf(resultSet.getString("user_role_name")));
        return user;
    }
}
