package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.PREFIX_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileUserPostActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;
    @Mock
    UserService userService;
    @InjectMocks
    ProfileUserPostAction action;

    User testUser = new User(1, User.Role.SPEAKER);
    String firstName = "Ivan";
    String lastName = "Ivanov";
    String receiveNotifications = "true";

    @Test
    public void testExecute() throws ServletException {
        when(req.getParameter("firstName")).thenReturn(firstName);
        when(req.getParameter("lastName")).thenReturn(lastName);
        when(req.getParameter("receiveNotifications")).thenReturn(receiveNotifications);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(testUser);

        String result = action.execute(req,resp);
        verify(userService).update(testUser);
        assertEquals(PREFIX_PATH + ControllerAction.HOME, result);
    }

}
