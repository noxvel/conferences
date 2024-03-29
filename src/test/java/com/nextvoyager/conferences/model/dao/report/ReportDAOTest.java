package com.nextvoyager.conferences.model.dao.report;

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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReportDAOTest {

    @Mock
    DAOFactory daoFactory;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @Mock
    Report.Status reportStatus;
    @Mock
    User mockUser;

    private final Report testReport = new Report();
    private final Event testEvent = new Event(1);
    List<Report> testList = new ArrayList<>();

    @Spy
    @InjectMocks
    ReportDAOMySQL dao;

    @BeforeEach
    public void setUp() {
        testReport.setId(1);
        testReport.setTopic("New in Java");
        testReport.setSpeaker(new User(1));
        testReport.setEvent(testEvent);
        testReport.setStatus(Report.Status.CONFIRMED);
        testReport.setDescription("New description for report");
        testList.add(testReport);

    }
    @Test
    public void find() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doReturn(testReport).when(dao).processReportRS((any(ResultSet.class)));
        assertEquals(testReport, dao.find(1));
    }

    @Test
    public void create() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt(anyInt())).thenReturn(1);

        assertThrows(IllegalArgumentException.class, () -> dao.create(testReport));
        testReport.setId(null);
        assertDoesNotThrow(() -> dao.create(testReport));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.update(testReport));

    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(),eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> dao.delete(testReport));
    }

    @Test
    public void list() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processReportListRS(any(ResultSet.class), any(List.class));
        dao.list();
        dao.list(testEvent);
        Mockito.verify(dao, Mockito.times(2)).processReportListRS(any(ResultSet.class), any(List.class));
    }

    @Test
    public void listWithPagination() throws SQLException, ClassNotFoundException {
        Mockito.when(daoFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(), eq(Statement.NO_GENERATED_KEYS))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.doNothing().when(dao).processReportListRS(any(ResultSet.class), any(ResultSet.class), any(ListWithCount.class));

        dao.listWithPagination(1, 6);
        dao.listWithPagination(1, 6, reportStatus);
        dao.listWithPagination(1, 6, mockUser);
        dao.listWithPagination(1, 6, mockUser, reportStatus);
        dao.listWithPagination(1, 6, testEvent);
        dao.listWithPagination(1, 6, testEvent, reportStatus);
        dao.listWithPagination(1, 6, testEvent, mockUser);
        dao.listWithPagination(1, 6, testEvent, mockUser, reportStatus);

        Mockito.verify(dao, Mockito.times(8)).processReportListRS(any(ResultSet.class),
                any(ResultSet.class), any(ListWithCount.class));

    }

}
