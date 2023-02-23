package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_TOKEN;
import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResetUserPasswordGetActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    private UserService userService;
    @InjectMocks
    ResetUserPasswordGetAction action;

    User.PasswordResetToken passwordResetToken = new User.PasswordResetToken();

    @Test
    void execute_withValidToken_shouldReturnUserResetPassword() throws Exception {
        String token = "validToken";
        passwordResetToken.setValid(true);

        when(req.getParameter(PARAM_USER_TOKEN)).thenReturn(token);
        when(userService.validatePasswordResetToken(anyString())).thenReturn(passwordResetToken);

        String result = action.execute(req, resp);

        assertEquals(USER_RESET_PASSWORD, result);
    }

    @Test
    void execute_withInvalidToken_shouldReturnUserShowMessage() throws Exception {
        String token = "invalidToken";
        passwordResetToken.setValid(false);

        when(req.getParameter(PARAM_USER_TOKEN)).thenReturn(token);
        when(userService.validatePasswordResetToken(anyString())).thenReturn(passwordResetToken);

        String result = action.execute(req, resp);

        assertEquals(PREFIX_PATH + USER_SHOW_MESSAGE + "?message=" + ShowMessageAction.Message.ResetPasswordError,
                result);
    }

    @Test
    void execute_withInvalidParameter_shouldThrowException() {
        when(req.getParameter(PARAM_USER_TOKEN)).thenReturn(null);

        assertThrows(ParameterValidationException.class, () -> action.execute(req, resp));
    }

}
