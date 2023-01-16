package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.controller.actions.event.EventCreateGetAction;
import com.nextvoyager.conferences.controller.filter.AuthorizationFilter;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled
@ExtendWith(MockitoExtension.class)
public class EventCreateGetActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;

    @InjectMocks
    EventCreateGetAction action;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testExecute() throws ServletException {
        String result = action.execute(req,resp);
        assertEquals(ControllerAction.EVENT_CREATE, result);
//        Mockito.verify(service, Mockito.times(1)).save(any(Tariff.class));

    }

}
