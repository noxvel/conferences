package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
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
public class SignOutUserActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;
    @InjectMocks
    SignOutUserAction action;

    @Test
    public void testExecute() throws ServletException {
        when(req.getSession()).thenReturn(session);

        String result = action.execute(req,resp);
        verify(session,times(1)).invalidate();
        assertEquals(PREFIX_PATH + ControllerAction.HOME, result);
    }
}
