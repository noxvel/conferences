package com.nextvoyager.conferences.controller.actions.report;

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

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.REPORT_VIEW;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportViewActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    ReportService reportService;

    @InjectMocks
    ReportViewAction action;

    @Test
    public void testExecute() {
        Report report = new Report();

        when(req.getParameter("reportID")).thenReturn("1");
        when(reportService.find(anyInt())).thenReturn(report);

        String result = assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(reportService, Mockito.times(1)).find(anyInt());
        assertEquals(REPORT_VIEW,result);
    }

}
