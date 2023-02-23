package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.model.entity.Event;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

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
    Event event = new Event(1);

    @InjectMocks
    EventRegisterUserAction action;

    @Test
    public void testExecute() {
        when(req.getParameter("eventID")).thenReturn("1");
        when(req.getParameter("register")).thenReturn("true");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(eventService.find(1)).thenReturn(event);

        assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(eventService, Mockito.times(1)).registerUser(any(Event.class),any(User.class),anyBoolean());
    }

}
