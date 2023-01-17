package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.actions.user.LoginUserGetAction;
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
public class LoginUserGetActionTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @InjectMocks
    LoginUserGetAction action;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testExecute() throws ServletException {
        String result = action.execute(req,resp);
        assertEquals(ControllerAction.USER_LOGIN, result);
//        Mockito.verify(service, Mockito.times(1)).save(any(Tariff.class));

    }
}