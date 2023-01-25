package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    ReportDAO reportDAO;
    @Mock
    UserDAO userDAO;

    private final Report testReport = new Report();
    List<Report> testList = new ArrayList<>();
    private final Event testEvent = new Event(1);

    @Mock
    User mockSpeaker;
    @Mock
    Report.Status reportStatus;

    @InjectMocks
    ReportServiceImpl reportService;

    ListWithCount<Report> testListWithCount = new ListWithCount<>();

    @BeforeEach
    public void setUp() {
        testReport.setId(1);
        testReport.setTopic("New in Java");
        testReport.setSpeaker(new User(1));
        testReport.setEvent(new Event(1));
        testReport.setStatus(Report.Status.CONFIRMED);
        testReport.setDescription("New description for report");
        testList.add(testReport);

        testListWithCount.setCount(1);
        testListWithCount.setList(testList);
    }

    @Test
    public void find() {
        Mockito.when(reportDAO.find(1)).thenReturn(testReport);
        Mockito.when(userDAO.find(testReport.getSpeaker().getId())).thenReturn(new User(1));
        assertEquals(testReport, reportService.find(1));
    }

    @Test
    public void create() {
        reportService.create(testReport);
        Mockito.verify(reportDAO, Mockito.times(1)).create(testReport);
    }

    @Test
    public void createWithApprovalReport() {
        reportService.create("", testReport, mockSpeaker);
        Mockito.verify(reportDAO, Mockito.times(1)).create(testReport);
    }

    @Test
    public void update() {
        reportService.update(testReport);
        Mockito.verify(reportDAO, Mockito.times(1)).update(testReport);
    }

    @Test
    public void updateWithApprovalReport() {
        reportService.update("", testReport, mockSpeaker);
        Mockito.verify(reportDAO, Mockito.times(1)).update(testReport);
    }
    @Test
    public void delete() {
        reportService.delete(testReport);
        Mockito.verify(reportDAO, Mockito.times(1)).delete(testReport);
    }

    @Test
    public void list() {
        Mockito.when(reportDAO.list(testEvent)).thenReturn(testList);
        assertEquals(testList, reportService.list(testEvent));
    }

    @Test
    public void listWithPagination(){
        Mockito.when(reportDAO.listWithPagination(1, 6)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, reportStatus)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, mockSpeaker)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, mockSpeaker, reportStatus)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, testEvent)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, testEvent, reportStatus)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, testEvent, mockSpeaker)).thenReturn(testListWithCount);
        Mockito.when(reportDAO.listWithPagination(1, 6, testEvent, mockSpeaker, reportStatus)).thenReturn(testListWithCount);

        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, reportStatus));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, mockSpeaker));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, mockSpeaker, reportStatus));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, testEvent));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, testEvent, reportStatus));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, testEvent, mockSpeaker));
        assertEquals(testListWithCount, reportDAO.listWithPagination( 1, 6, testEvent, mockSpeaker, reportStatus));
    }
}
