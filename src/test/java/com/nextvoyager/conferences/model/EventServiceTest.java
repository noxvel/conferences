package com.nextvoyager.conferences.model;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled
public class EventServiceTest {

    @Mock
    EventDAO dao;

    private Event testEvent = new Event();
    List<Event> testList = new ArrayList<>();

    @InjectMocks
    EventServiceImpl eventService;

    @BeforeEach
    public void setUp() {
        testEvent.setId(1);
        testEvent.setName("Java conference");
        testEvent.setPlace("Odesa");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date endDate = format.parse("2022/10/14 12:00:00");
            Date beginDate = format.parse("2022/10/12 12:00:00");
            testEvent.setBeginDate(beginDate);
            testEvent.setEndDate(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        testEvent.setParticipantsCame(100);
        testEvent.setDescription("New description");
        testList.add(testEvent);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        Mockito.when(dao.list(EventDAO.SortType.Date, EventDAO.SortDirection.Ascending, EventDAO.TimeFilter.AllTime)).thenReturn(testList);
        assertEquals(testList, eventService.list(EventDAO.SortType.Date, EventDAO.SortDirection.Ascending, EventDAO.TimeFilter.AllTime));
    }

    @Test
    public void find() {
        Mockito.when(dao.find(1)).thenReturn(testEvent);
        assertEquals(testEvent, eventService.find(1));
    }

    @Test
    public void save() {
        eventService.create(testEvent);
        Mockito.verify(dao, Mockito.times(1)).create(testEvent);
    }

    @Test
    public void update() {
        eventService.update(testEvent);
        Mockito.verify(dao, Mockito.times(1)).update(testEvent);
    }

    @Test
    public void remove() {
        dao.delete(testEvent);
        Mockito.verify(dao, Mockito.times(1)).delete(testEvent);
    }


}
