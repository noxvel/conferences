package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    public static EventServiceImpl getInstance(EventDAO eventDAO) {
        return new EventServiceImpl(eventDAO);
    }

    private EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public Event find(Integer id) {
        return eventDAO.find(id);
    }

    @Override
    public void update(Event event) {
        eventDAO.update(event);
    }

    @Override
    public void create(Event event) {
        eventDAO.create(event);
    }

    @Override
    public void delete(Event event) {
        eventDAO.delete(event);
    }

    @Override
    public boolean isUserRegisterEvent(Event event, User user) {
        return eventDAO.isUserRegisterEvent(event, user);
    }

    @Override
    public void registerUser(Integer eventID, User user, boolean register) {
        eventDAO.registerUser(eventID,user,register);
    }

    @Override
    public List<Event> list(EventDAO.SortType sortType, EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter) {
        return eventDAO.list(sortType, sortDirection, timeFilter);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPagination(Integer page, Integer limit, EventDAO.SortType sortType,
                                                           EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter) {
        return eventDAO.listWithPagination(page, limit, sortType, sortDirection, timeFilter);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPaginationSpeaker(int page, int limit, EventDAO.SortType sortType,
                                                                             EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                                  User speaker, Boolean participated) {
        return eventDAO.listWithPaginationSpeaker(page, limit, sortType, sortDirection, timeFilter, speaker, participated);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPaginationOridnaryUser(int page, int limit, EventDAO.SortType sortType,
                                                                       EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                                       User ordinaryUser, Boolean participated) {
        return eventDAO.listWithPaginationOrdinaryUser(page, limit, sortType, sortDirection, timeFilter, ordinaryUser, participated);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPaginationReportStatusFilter(int page,
                                                                             int limit, EventDAO.SortType sortType,
                                                                             EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                                             Report.Status reportStatus) {
        return eventDAO.listWithPaginationReportStatusFilter(page, limit, sortType, sortDirection,timeFilter, reportStatus);
    }
}
