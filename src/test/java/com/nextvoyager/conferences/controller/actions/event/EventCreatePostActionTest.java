package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EventCreatePostActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    EventService eventService;

    @InjectMocks
    EventCreatePostAction action;

    @Test
    public void testExecute() {
        Mockito.when(req.getParameter("name")).thenReturn("test event");
        Mockito.when(req.getParameter("place")).thenReturn("Tokyo");
        Mockito.when(req.getParameter("beginDate")).thenReturn("2022-01-01T00:00");
        Mockito.when(req.getParameter("endDate")).thenReturn("2022-01-05T00:00");
        Mockito.when(req.getParameter("description")).thenReturn("test description");

        assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(eventService, Mockito.times(1)).create(any(Event.class));
    }

}
