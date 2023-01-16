package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.actions.report.ReportCreateGetAction;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled
@ExtendWith(MockitoExtension.class)
public class ReportCreateGetActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @Mock
    DataSource dataSource;

    @InjectMocks
    ReportCreateGetAction action;

    private User speaker = new User(1, User.Role.SPEAKER);
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        userList.add(speaker);

    }

    @Test
    public void testExecute() throws ServletException {
        Mockito.when(req.getParameter("eventID")).thenReturn("1");
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(userService.listWithOneRole(User.Role.SPEAKER)).thenReturn(userList);
        Mockito.when(session.getAttribute("user")).thenReturn(speaker);
        String result = action.execute(req,resp);
        assertEquals(ControllerAction.REPORT_CREATE, result);
//        Mockito.verify(service, Mockito.times(1)).save(any(Tariff.class));

    }
}
