package com.nextvoyager.conferences.controller.actions.user.speaker;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpeakerReportApprovalActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    ReportService reportService;
    @Mock
    HttpSession session;
    @InjectMocks
    SpeakerReportApprovalAction action;

    @Test
    public void testExecute() throws Exception {
        User speaker = new User(1);
        Report report = new Report();

        when(req.getParameter("reportID")).thenReturn("1");
        when(req.getParameter("action")).thenReturn("accept-propose-speaker");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(speaker);
        when(reportService.find(1)).thenReturn(report);

        assertDoesNotThrow(() -> action.execute(req,resp));
        verify(reportService).update(anyString(), eq(report), eq(speaker));
    }
}
