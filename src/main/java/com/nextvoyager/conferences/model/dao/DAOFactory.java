package com.nextvoyager.conferences.model.dao;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.dao.event.EventDAOMySQL;
import com.nextvoyager.conferences.model.dao.exeption.*;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.report.ReportDAOMySQL;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAOMySQL;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This abstract class represents a DAO factory for a SQL database. You can use {@link #getInstance(String)}
 * to obtain an instance for the given database name. The specific instance returned depends on
 * <p>
 * This class requires a properties file named 'dao.properties' in the classpath with among others
 * the following properties:
 * <pre>
 * name.url
 * name.username
 * name.password
 * </pre>
 * Properties username and password are optional.
 * <ul>
 * <li>The 'name' must represent the database name in {@link #getInstance(String)}.</li>
 * <li>The 'name.url' must represent either the JNDI name of the database.</li>
 * <li>The 'name.username' must represent the username of the database login.</li>
 * <li>The 'name.password' must represent the password of the database login.</li>
 * </ul>
 * When using JNDI with username/password preconfigured, you can omit the username and password properties as well.
 * <p>
 * Here are basic example of valid property for a database with the name 'base':
 * <pre>
 * base.jndi.url = java:comp/env/jdbc/base
 * </pre>
 * Here is a basic use example:
 * <pre>
 * DAOFactory base = DAOFactory.getInstance("base.jndi");
 * Report reportDAO = base.getReportDAO();
 * </pre>
 */
public abstract class DAOFactory {

    private static DAOFactory instance;
    // Constants ----------------------------------------------------------------------------------

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";
    private static final String DATABASE_NAME = "conferences.jndi";

    // Actions ------------------------------------------------------------------------------------

    public static synchronized DAOFactory getInstance() throws DAOConfigurationException {
        if (instance == null) {
            instance = getInstance(DATABASE_NAME);
        }
        return instance;
    }

    /**
     * Returns a new DAOFactory instance for the given database name.
     * @param name The database name to return a new DAOFactory instance for.
     * @return A new DAOFactory instance for the given database name.
     * @throws DAOConfigurationException If the database name is null, or if the properties file is
     * missing in the classpath or cannot be loaded, or if a required property is missing in the
     * properties file, or if either the driver cannot be loaded or the datasource cannot be found.
     */
    private static synchronized DAOFactory getInstance(String name) throws DAOConfigurationException {
        if (name == null) {
            throw new DAOConfigurationException("Database name is null.");
        }

        DAOProperties properties = new DAOProperties(name);
        String url = properties.getProperty(PROPERTY_URL, true);
        String password = properties.getProperty(PROPERTY_PASSWORD, false);
        String username = properties.getProperty(PROPERTY_USERNAME, password != null);

        // Assume URL as DataSource URL and lookup it in the JNDI.
        DataSource dataSource;
        try {
            dataSource = (DataSource) new InitialContext().lookup(url);
        } catch (NamingException e) {
            throw new DAOConfigurationException(
                    "DataSource '" + url + "' is missing in JNDI.", e);
        }
        if (username != null) {
            instance = new DataSourceWithLoginDAOFactory(dataSource, username, password);
        } else {
            instance = new DataSourceDAOFactory(dataSource);
        }

        return instance;
    }

    /**
     * Returns a connection to the database.
     * @return A connection to the database.
     * @throws SQLException If acquiring the connection fails.
     */
    public abstract Connection getConnection() throws SQLException, ClassNotFoundException;

    public UserDAO getUserDAO(){
        return new UserDAOMySQL(instance);
    }
    public ReportDAO getReportDAO(){
        return new ReportDAOMySQL(instance);
    }
    public EventDAO getEventDAO(){
        return new EventDAOMySQL(instance);
    }

}

/**
 * The DataSource based DAOFactory.
 */
class DataSourceDAOFactory extends DAOFactory {
    private final DataSource dataSource;

    DataSourceDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

/**
 * The DataSource-with-Login based DAOFactory.
 */
class DataSourceWithLoginDAOFactory extends DAOFactory {
    private final DataSource dataSource;
    private final String username;
    private final String password;

    DataSourceWithLoginDAOFactory(DataSource dataSource, String username, String password) {
        this.dataSource = dataSource;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection(username, password);
    }
}