package com.nextvoyager.conferences.model;

import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportServiceTest {


    @Mock
    ReportDAO dao;

    private Report testReport = new Report();
    List<Report> testList = new ArrayList<>();

    @InjectMocks
    ReportServiceImpl reportService;

    @BeforeEach
    public void setUp() {
        testReport.setId(1);
        testReport.setTopic("New in Java");
        testReport.setSpeaker(new User());
        testReport.setEvent(new Event(1));
        testReport.setDescription("New description for report");
        testList.add(testReport);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        Mockito.when(dao.list(1)).thenReturn(testList);
        assertEquals(testList, reportService.list(1));
    }

//    @Test
//    public void find() {
//        Mockito.when(dao.find(1)).thenReturn(testReport);
//        assertEquals(testReport, reportService.find(1));
//    }

    @Test
    public void save() {
        reportService.create(testReport);
        Mockito.verify(dao, Mockito.times(1)).create(testReport);
    }

    @Test
    public void update() {
        reportService.update(testReport);
        Mockito.verify(dao, Mockito.times(1)).update(testReport);
    }

    @Test
    public void remove() {
        dao.delete(testReport);
        Mockito.verify(dao, Mockito.times(1)).delete(testReport);
    }

}
