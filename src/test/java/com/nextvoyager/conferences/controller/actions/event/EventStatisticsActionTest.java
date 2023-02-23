package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
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

import java.util.ArrayList;

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.EVENT_STATISTICS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventStatisticsActionTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    EventService eventService;
    @Mock
    HttpSession session;

    @InjectMocks
    EventStatisticsAction action;

    @Test
    public void testExecute() {
        ListWithCount<Event> listResult = new ListWithCount<>();
        listResult.setCount(1);
        listResult.setList(new ArrayList<>());

        when(req.getParameter("page")).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("eventListSortType")).thenReturn(null);
        when(session.getAttribute("eventListSortDirection")).thenReturn(null);
        when(session.getAttribute("eventTimeFilter")).thenReturn(null);
        when(eventService.listWithPagination(anyInt(), anyInt(), any(EventDAO.SortType.class),
                any(EventDAO.SortDirection.class), any(EventDAO.TimeFilter.class))).thenReturn(listResult);

        String result = assertDoesNotThrow(() -> action.execute(req,resp));
        Mockito.verify(eventService, times(1)) .listWithPagination(anyInt(), anyInt(),
                any(EventDAO.SortType.class), any(EventDAO.SortDirection.class), any(EventDAO.TimeFilter.class));
        assertEquals(EVENT_STATISTICS, result);
    }

}
