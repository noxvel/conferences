package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeUserPasswordPostActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;
    @Mock
    UserService userService;
    @InjectMocks
    ChangeUserPasswordPostAction action;

    User testUser = new User(1);

    @Test
    public void wrongCurrentPassword() throws Exception {
        when(req.getParameter("currentPassword")).thenReturn("123");
        when(req.getParameter("newPassword")).thenReturn("1234");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(testUser);

        when(userService.checkPassword(testUser)).thenReturn(false);

        String result = action.execute(req,resp);
        assertEquals(ControllerAction.USER_CHANGE_PASSWORD, result);
    }

    @Test
    public void goodCurrentPassword() throws Exception {
        when(req.getParameter("currentPassword")).thenReturn("123");
        when(req.getParameter("newPassword")).thenReturn("1234");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(testUser);

        when(userService.checkPassword(testUser)).thenReturn(true);

        String result = action.execute(req,resp);
        verify(userService,times(1)).changePassword(testUser);
        assertEquals(PREFIX_PATH + ControllerAction.USER_PROFILE, result);
    }
}
