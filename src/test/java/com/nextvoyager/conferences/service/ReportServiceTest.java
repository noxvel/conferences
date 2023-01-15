package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled
public class ReportServiceTest {

    @Mock
    ReportDAO reportDAO;
    @Mock
    UserDAO userDAO;

    private Report testReport = new Report();
    List<Report> testList = new ArrayList<>();

    @InjectMocks
    ReportServiceImpl reportService;

    @BeforeEach
    public void setUp() {
        testReport.setId(1);
        testReport.setTopic("New in Java");
        testReport.setSpeaker(new User(1));
        testReport.setEvent(new Event(1));
        testReport.setStatus(Report.Status.CONFIRMED);
        testReport.setDescription("New description for report");
        testList.add(testReport);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        Mockito.when(reportDAO.list(1)).thenReturn(testList);
        assertEquals(testList, reportService.list(1));
    }

    @Test
    public void find() {
        Mockito.when(reportDAO.find(1)).thenReturn(testReport);
        Mockito.when(userDAO.find(testReport.getSpeaker().getId())).thenReturn(new User(1));
        assertEquals(testReport, reportService.find(1));
    }

    @Test
    public void save() {
        reportService.create(testReport);
        Mockito.verify(reportDAO, Mockito.times(1)).create(testReport);
    }

    @Test
    public void update() {
        reportService.update(testReport);
        Mockito.verify(reportDAO, Mockito.times(1)).update(testReport);
    }

    @Test
    public void remove() {
        reportService.delete(testReport);
        Mockito.verify(reportDAO, Mockito.times(1)).delete(testReport);
    }

}
