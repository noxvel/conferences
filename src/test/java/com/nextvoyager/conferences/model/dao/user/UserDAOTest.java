package com.nextvoyager.conferences.model.dao.user;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.util.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    @Mock
    DAOFactory daoFactory;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @Mock
    User.Role role;
    @Mock
    Event event;

    private final User testUser = new User();
    List<User> testList = new ArrayList<>();

    @Spy
    @InjectMocks
    UserDAOMySQL dao;

    @BeforeEach
    public void setUp() {
        testUser.setId(1);
        testUser.setEmail("user@mail.com");
        testUser.setFirstName("Ivan");
        testUser.setLastName("Garmata");
        testUser.setPassword("123");
        testUser.setRole(User.Role.ORDINARY_USER);
        testList.add(testUser);
    }

    @Test
    public void findByID() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doReturn(testUser).when(dao).processUserRS(any(ResultSet.class));
        assertEquals(testUser, dao.find(1));

        Mockito.verify(dao, Mockito.times(1)).processUserRS(any(ResultSet.class));
    }

    @Test
    public void findByEmailAndPassword() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("password")).thenReturn("passwords hash");
        when(resultSet.getInt("id")).thenReturn(1);

        Mockito.doReturn(testUser).when(dao).processUserRS(any(ResultSet.class));

        try (MockedStatic<PasswordEncoder> mocked = mockStatic(PasswordEncoder.class)) {
            // Mocking
            mocked.when(() -> PasswordEncoder.check(anyString(),anyString())).thenReturn(true);

            assertEquals(testUser, dao.find("email", "password"));
            Mockito.verify(dao, Mockito.times(1)).processUserRS(any(ResultSet.class));
        }
    }

    @Test
    public void create() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> dao.create(testUser));
        testUser.setId(null);
        assertDoesNotThrow(() -> dao.create(testUser));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.update(testUser));

    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.delete(testUser));
    }

    @Test
    public void existEmail() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        assertTrue(dao.existEmail("test@mail.com"));
    }

    @Test
    public void checkPassword() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("password")).thenReturn("passwords hash");

        try (MockedStatic<PasswordEncoder> mocked = mockStatic(PasswordEncoder.class)) {
            // Mocking
            mocked.when(() -> PasswordEncoder.check(anyString(),anyString())).thenReturn(true);
            assertTrue(dao.checkPassword(testUser));
        }
    }

    @Test
    public void changePassword() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.changePassword(testUser));
    }

    @Test
    public void list() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processUserListRS(any(ResultSet.class),any(ResultSet.class), any(ListWithCount.class));

        dao.list(1, 12);
        Mockito.verify(dao, Mockito.times(1)).processUserListRS(any(ResultSet.class),
                any(ResultSet.class), any(ListWithCount.class));
    }

    @Test
    public void additionalList() throws SQLException, ClassNotFoundException {
        when(daoFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processUserListRS(any(ResultSet.class), any(List.class));

        dao.listWithOneRole(role);
        dao.receiveEventNotificationsList(event);
        Mockito.verify(dao, Mockito.times(2)).processUserListRS(any(ResultSet.class), any(List.class));
    }

}
