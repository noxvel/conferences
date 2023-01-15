package com.nextvoyager.conferences.model;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.user.UserDAOMySQL;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
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

    private User testUser = new User();
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
    public void find() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doReturn(testUser).when(dao).processUserRS(any(ResultSet.class));
        assertEquals(testUser, dao.find(1));
        assertEquals(testUser, dao.find("email", "password"));

        Mockito.verify(dao, Mockito.times(2)).processUserRS(any(ResultSet.class));

//        // Mock scope for use static methods
//        try (MockedStatic<EventDAOMySQL> mocked = mockStatic(EventDAOMySQL.class)) {
//            // Mocking
//            mocked.when(() -> EventDAOMySQL.map(resultSet)).thenReturn(testEvent);
//            assertEquals(testEvent, dao.find(1));
//        }
    }

    @Test
    public void create() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt(anyInt())).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> dao.create(testUser));
        testUser.setId(null);
        assertDoesNotThrow(() -> dao.create(testUser));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.update(testUser));

    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.delete(testUser));
    }

    @Test
    public void existEmail() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);

        assertTrue(dao.existEmail("test@mail.com"));
    }

    @Test
    public void checkPassword() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);

        assertTrue(dao.checkPassword(testUser));
    }

    @Test
    public void changePassword() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.changePassword(testUser));
    }

    @Test
    public void list() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processUserListRS(any(ResultSet.class), any(List.class));

        dao.list();
        dao.listWithOneRole(role);
        dao.receiveEventNotificationsList(event);
        Mockito.verify(dao, Mockito.times(3)).processUserListRS(any(ResultSet.class), any(List.class));
//        assertEquals(testList, dao.list(sortType,sortDirection,timeFilter));
    }

}
