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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUserPostActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;
    @Mock
    UserService userService;
    @InjectMocks
    LoginUserPostAction action;

    User testUser = new User(1, User.Role.SPEAKER);
    String email = "user@email.com";
    String password = "123";

    @Test
    public void correctLogin() throws ServletException {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        when(userService.find(email,password)).thenReturn(testUser);
        when(req.getSession()).thenReturn(session);

        String result = action.execute(req,resp);
        verify(session).setAttribute("user", testUser);
        verify(session).setAttribute("userRole", testUser.getRole());
        assertEquals(PREFIX_PATH + ControllerAction.HOME, result);
    }

    @Test
    public void wrongLogin() throws ServletException {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        when(userService.find(email,password)).thenReturn(null);

        String result = action.execute(req,resp);
        assertEquals(ControllerAction.USER_LOGIN, result);
    }
}
