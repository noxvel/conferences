package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
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
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EventRegisterUserActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    EventService eventService;
    @Mock
    HttpSession session;

    User user = new User();

    @InjectMocks
    EventRegisterUserAction action;

    @Test
    public void testExecute() {
        Mockito.when(req.getParameter("eventID")).thenReturn("1");
        Mockito.when(req.getParameter("register")).thenReturn("true");
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(user);

        assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(eventService, Mockito.times(1)).registerUser(anyInt(),any(User.class),anyBoolean());
    }

}
