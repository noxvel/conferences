package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportListFilterActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;

    @InjectMocks
    ReportListFilterAction action;

    @Test
    public void executeWithNoReportStatusFilter() {
        when(req.getParameter("redirectPath")).thenReturn("/home");
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("reportStatusFilter")).thenReturn("");

        assertDoesNotThrow(() -> action.execute(req,resp));

        Mockito.verify(session, Mockito.times(1)).removeAttribute("reportStatusFilter");
    }

    @Test
    public void executeWithReportStatusFilter() {
        when(req.getParameter("redirectPath")).thenReturn("/home");
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("reportStatusFilter")).thenReturn("FREE");

        assertDoesNotThrow(() -> action.execute(req,resp));

        Mockito.verify(session, Mockito.times(1)).setAttribute("reportStatusFilter",Report.Status.FREE);
    }

}
