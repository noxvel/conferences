package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportDeleteActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    ReportService reportService;

    @InjectMocks
    ReportDeleteAction action;

    @Test
    public void testExecute() {
        Report report = new Report();
        report.setEvent(new Event(1));

        when(req.getParameter("reportID")).thenReturn("1");
        when(reportService.find(anyInt())).thenReturn(report);

        assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(reportService, Mockito.times(1)).find(anyInt());
        Mockito.verify(reportService, Mockito.times(1)).delete(any(Report.class));
    }

}
