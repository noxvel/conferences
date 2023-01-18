package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
public class EventDeleteActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    EventService eventService;

    @InjectMocks
    EventDeleteAction action;

    Event event = new Event(1, "test");

    @Test
    public void testExecute() {
        when(req.getParameter("eventID")).thenReturn("1");
        when(eventService.find(anyInt())).thenReturn(event);

        assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(eventService, Mockito.times(1)).find(anyInt());
        Mockito.verify(eventService, Mockito.times(1)).delete(any(Event.class));
    }

}
