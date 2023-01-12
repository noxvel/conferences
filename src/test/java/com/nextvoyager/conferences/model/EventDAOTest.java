package com.nextvoyager.conferences.model;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.dao.event.EventDAOMySQL;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.dao.utils.DAOUtil;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class EventDAOTest {

//    EventDAO dao = AppContext.getInstance().getEventDAO();

    @Mock
    DAOFactory daoFactory;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    DAOUtil.ValueDAO valueDAO;

    private Event testEvent = new Event();
    private User testUser = new User(1, User.Role.ORDINARY_USER);
    List<Event> testList = new ArrayList<>();

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

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void find() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        dao.find(1);
        Mockito.verify(resultSet, Mockito.times(1)).next();
//        assertEquals(testEvent, dao.find(1));
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

        assertDoesNotThrow(() -> dao.registerUser(1,testUser,true));
    }

    @Test
    public void isUserRegisterEvent() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        assertFalse(dao.isUserRegisterEvent(testEvent, testUser));
    }

    @Test
    public void list(){
//        List<Event> list(EventDAO.SortType sortType, EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter) throws DAOException;
//
//        EventDAO.ListWithCountResult listWithPagination(Integer page, Integer limit, EventDAO.SortType sortType,
//                EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter) throws DAOException;
//
//        EventDAO.ListWithCountResult listWithPaginationReportStatusFilter(int page, int limit, EventDAO.SortType sortType,
//        EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
//        Report.Status status) throws DAOException;
//
//        EventDAO.ListWithCountResult listWithPaginationSpeaker(int page, int limit, EventDAO.SortType sortType,
//        EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
//        User speaker, Boolean participated);
//
//        EventDAO.ListWithCountResult listWithPaginationOrdinaryUser(int page, int limit, EventDAO.SortType sortType,
//        EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
//        User ordinaryUser, Boolean participated);

    }

}
