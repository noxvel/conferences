package com.nextvoyager.conferences.model.dao.event;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EventDAOTest {

    @Mock
    DAOFactory daoFactory;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @Mock
    EventDAO.SortType sortType;
    @Mock
    EventDAO.SortDirection sortDirection;
    @Mock
    EventDAO.TimeFilter timeFilter;
    @Mock
    Report.Status reportStatus;
    @Mock
    User mockUser;

    private final Event testEvent = new Event();
    private final User testUser = new User(1, User.Role.ORDINARY_USER);
    List<Event> testList = new ArrayList<>();

    @Spy
    @InjectMocks
    EventDAOMySQL dao;

    @BeforeEach
    public void setUp(){
        testEvent.setId(1);
        testEvent.setName("Java conference");
        testEvent.setPlace("Odesa");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime endDate = LocalDateTime.parse("2022/10/14 12:00:00", format);
        LocalDateTime beginDate = LocalDateTime.parse("2022/10/12 12:00:00", format);
        testEvent.setBeginDate(beginDate);
        testEvent.setEndDate(endDate);
        testEvent.setParticipantsCame(100);
        testEvent.setDescription("New description");
        testList.add(testEvent);
    }

    @Test
    public void find() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doReturn(testEvent).when(dao).processEventRS(any(ResultSet.class));
        assertEquals(testEvent, dao.find(1));
    }

    @Test
    public void create() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt(anyInt())).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> dao.create(testEvent));
        testEvent.setId(null);
        assertDoesNotThrow(() -> dao.create(testEvent));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.update(testEvent));

    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.delete(testEvent));
    }

    @Test
    public void registerUser() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.registerUser(testEvent,testUser,true));
    }

    @Test
    public void isUserRegisterEvent() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        assertFalse(dao.isUserRegisterEvent(testEvent, testUser));
    }

    @Test
    public void list() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processEventListRS(any(ResultSet.class), any(List.class));
        dao.list(sortType, sortDirection, timeFilter);
        Mockito.verify(dao, Mockito.times(1)).processEventListRS(any(ResultSet.class), any(List.class));
//        assertEquals(testList, dao.list(sortType,sortDirection,timeFilter));

    }

    @Test
    public void listWithPagination() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processEventListRS(any(ResultSet.class), any(ResultSet.class), any(ListWithCount.class));

        dao.listWithPagination(1, 6, sortType, sortDirection, timeFilter);
        dao.listWithPaginationReportStatusFilter(1, 6, sortType, sortDirection, timeFilter, reportStatus);
        dao.listWithPaginationSpeaker(1, 6, sortType, sortDirection, timeFilter, mockUser, false);
        dao.listWithPaginationOrdinaryUser(1, 6, sortType, sortDirection, timeFilter, mockUser, false);

        Mockito.verify(dao, Mockito.times(4)).processEventListRS(any(ResultSet.class),
                any(ResultSet.class), any(ListWithCount.class));

    }

}
