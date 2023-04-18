package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventDAO dao;
    @Mock
    User mockUser;
    @Mock
    UserDAO userDAO;

    @Mock
    EventDAO.SortType sortType;
    @Mock
    EventDAO.SortDirection sortDirection;
    @Mock
    EventDAO.TimeFilter timeFilter;
    @Mock
    Report.Status reportStatus;

    private final Event testEvent = new Event();
    private final List<User> emtpyUserList = new ArrayList<>();
    List<Event> testList = new ArrayList<>();

    ListWithCount<Event> testListWithCount = new ListWithCount<>();

    @InjectMocks
    EventServiceImpl eventService;

    @BeforeEach
    public void setUp() {
        testEvent.setId(1);
        testEvent.setName("Java conference");
        testEvent.setPlace("Odesa");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime endDate = LocalDateTime.parse("2022/10/14 12:00:00", format);
        LocalDateTime beginDate = LocalDateTime.parse("2022/10/12 12:00:00", format);
        testEvent.setBeginDate(beginDate);
        testEvent.setEndDate(endDate);
        testEvent.setParticipantsCame(100);
        testEvent.setDescription("New description");
        testList.add(testEvent);

        testListWithCount.setCount(1);
        testListWithCount.setList(testList);
    }

    @Test
    public void find() {
        Mockito.when(dao.find(1)).thenReturn(testEvent);
        assertEquals(testEvent, eventService.find(1));
    }

    @Test
    public void create() {
        eventService.create(testEvent);
        Mockito.verify(dao, Mockito.times(1)).create(testEvent);
    }

    @Test
    public void update() {
        Mockito.when(userDAO.receiveEventNotificationsList(testEvent)).thenReturn(emtpyUserList);
        eventService.update(testEvent, true);
        Mockito.verify(dao, Mockito.times(1)).update(testEvent);
    }

    @Test
    public void delete() {
//        Mockito.when(userDAO.receiveEventNotificationsList(testEvent)).thenReturn(emtpyUserList);
        eventService.delete(testEvent);
        Mockito.verify(dao, Mockito.times(1)).delete(testEvent);
    }

    @Test
    public void isUserRegisterEvent() {
        eventService.isUserRegisterEvent(testEvent, mockUser);
        Mockito.verify(dao, Mockito.times(1)).isUserRegisterEvent(testEvent, mockUser);
    }

    @Test
    public void registerUser() {
        eventService.registerUser(testEvent,mockUser,true);
        Mockito.verify(dao, Mockito.times(1)).registerUser(testEvent,mockUser,true);
    }

    @Test
    public void list() {
        Mockito.when(dao.list(sortType, sortDirection, timeFilter)).thenReturn(testList);
        assertEquals(testList, eventService.list(sortType, sortDirection, timeFilter));
    }

    @Test
    public void listWithPagination() {

        Mockito.when(dao.listWithPagination(
                1, 6, sortType, sortDirection, timeFilter)).thenReturn(testListWithCount);
        Mockito.when(dao.listWithPaginationSpeaker(
                1, 6, sortType, sortDirection, timeFilter, mockUser, true)).thenReturn(testListWithCount);
        Mockito.when(dao.listWithPaginationOrdinaryUser(
                1, 6, sortType, sortDirection, timeFilter, mockUser, true)).thenReturn(testListWithCount);
        Mockito.when(dao.listWithPaginationReportStatusFilter(
                1, 6, sortType, sortDirection,timeFilter, reportStatus)).thenReturn(testListWithCount);

        assertEquals(testListWithCount, eventService.listWithPaginationCommon(
                1, 6, sortType, sortDirection, timeFilter));
        assertEquals(testListWithCount, eventService.listWithPaginationSpeaker(
                1, 6, sortType, sortDirection, timeFilter, mockUser, true));
        assertEquals(testListWithCount, eventService.listWithPaginationOrdinaryUser(
                1, 6, sortType, sortDirection, timeFilter, mockUser, true));
        assertEquals(testListWithCount, eventService.listWithPaginationReportStatusFilter(
                1, 6, sortType, sortDirection,timeFilter, reportStatus));

    }

}
