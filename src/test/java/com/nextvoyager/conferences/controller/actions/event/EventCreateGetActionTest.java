package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class EventCreateGetActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;

    @InjectMocks
    EventCreateGetAction action;

    @Test
    public void testExecute() throws Exception {
        String result = action.execute(req,resp);
        assertEquals(ControllerAction.EVENT_CREATE, result);
    }

}
