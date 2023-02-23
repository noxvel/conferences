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

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.EVENT_EDIT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventEditGetActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    EventService eventService;

    @InjectMocks
    EventEditGetAction action;

    Event event = new Event(1, "test");

    @Test
    public void testExecute() {
        when(req.getParameter("eventID")).thenReturn("1");
        when(eventService.find(anyInt())).thenReturn(event);

        String result = assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(eventService, Mockito.times(1)).find(anyInt());
        assertEquals(EVENT_EDIT, result);
    }

}
