package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.recaptcha.RecaptchaUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
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
    String recaptchaResponse = "qwerty";

    @Test
    public void correctLogin() throws Exception {
        when(req.getParameter(PARAM_USER_EMAIL)).thenReturn(email);
        when(req.getParameter(PARAM_USER_PASSWORD)).thenReturn(password);
        when(userService.find(email,password)).thenReturn(testUser);
        when(req.getSession()).thenReturn(session);

        String result = action.execute(req,resp);
        verify(session).setAttribute("user", testUser);
        verify(session).setAttribute("userRole", testUser.getRole());
        assertEquals(PREFIX_PATH + ControllerAction.HOME, result);
    }

    @Test
    public void wrongLogin() throws Exception {
        when(req.getParameter(PARAM_USER_EMAIL)).thenReturn(email);
        when(req.getParameter(PARAM_USER_PASSWORD)).thenReturn(password);
        when(userService.find(email,password)).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        String result = action.execute(req,resp);
        assertEquals(ControllerAction.USER_LOGIN, result);
    }

    @Test
    public void tooManyLoginAttemptsAndCorrectRecaptcha() throws Exception {
        when(req.getParameter(PARAM_USER_EMAIL)).thenReturn(email);
        when(req.getParameter(PARAM_USER_PASSWORD)).thenReturn(password);
        when(req.getParameter(PARAM_USER_RECAPTCHA)).thenReturn(recaptchaResponse);
        when(userService.find(email,password)).thenReturn(null);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("loginAttempts")).thenReturn(3);

        try (MockedStatic<RecaptchaUtil> mocked = mockStatic(RecaptchaUtil.class)) {
            // Mocking
            mocked.when(() -> RecaptchaUtil.verify(anyString())).thenReturn(true);

            String result = action.execute(req,resp);
            verify(req).setAttribute("recaptchaSiteKey", RecaptchaUtil.SITE_KEY);
            verify(userService).find(anyString(), anyString());
            assertEquals(ControllerAction.USER_LOGIN, result);
        }
    }

    @Test
    public void tooManyLoginAttemptsAndBadRecaptcha() throws Exception {
        when(req.getParameter(PARAM_USER_EMAIL)).thenReturn(email);
        when(req.getParameter(PARAM_USER_RECAPTCHA)).thenReturn(null);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("loginAttempts")).thenReturn(3);

        String result = action.execute(req,resp);
        verify(req).setAttribute("recaptchaSiteKey", RecaptchaUtil.SITE_KEY);
        verifyNoInteractions(userService);
        assertEquals(ControllerAction.USER_LOGIN, result);
    }
}
