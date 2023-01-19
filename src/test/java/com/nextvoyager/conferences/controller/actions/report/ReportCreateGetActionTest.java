package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
public class ReportCreateGetActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;

    @InjectMocks
    ReportCreateGetAction action;

    private User speaker = new User(1, User.Role.SPEAKER);
    private User moderator = new User(2, User.Role.MODERATOR);
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        userList.add(speaker);
    }

    @Test
    public void testExecuteForSpeaker() throws ServletException {
        Mockito.when(req.getParameter("eventID")).thenReturn("1");
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(speaker);

        String result = action.execute(req,resp);
        Mockito.verify(userService, Mockito.times(0)).listWithOneRole(User.Role.SPEAKER);
        assertEquals(ControllerAction.REPORT_CREATE, result);

    }

    @Test
    public void testExecuteForModerator() throws ServletException {
        Mockito.when(req.getParameter("eventID")).thenReturn("1");
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(userService.listWithOneRole(User.Role.SPEAKER)).thenReturn(userList);

        Mockito.when(session.getAttribute("user")).thenReturn(moderator);
        String result = action.execute(req,resp);
        Mockito.verify(userService, Mockito.times(1)).listWithOneRole(User.Role.SPEAKER);
        assertEquals(ControllerAction.REPORT_CREATE, result);

    }
}
