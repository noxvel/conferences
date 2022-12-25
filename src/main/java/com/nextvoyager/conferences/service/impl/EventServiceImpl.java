package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    DAOFactory javabase = DAOFactory.getInstance();
    EventDAO eventDAO = javabase.getEventDAO();

    public static EventServiceImpl getInstance() {
        return new EventServiceImpl();
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
    public boolean isUserRegisterEvent(Event event, User user) {
        return eventDAO.isUserRegisterEvent(event, user);
    }

    @Override
    public void registerUser(Integer eventID, User user, boolean register) {
        eventDAO.registerUser(eventID,user,register);
    }

    @Override
    public List<Event> list(EventDAO.SortType sortType, EventDAO.SortDirection sortDirection) {
        return eventDAO.list(sortType, sortDirection);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPagination(Integer page, Integer limit, EventDAO.SortType sortType,
                                                           EventDAO.SortDirection sortDirection) {
        return eventDAO.listWithPagination(page, limit, sortType, sortDirection);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPaginationSpeaker(int page, int limit, EventDAO.SortType sortType,
                                                                             EventDAO.SortDirection sortDirection,
                                                                  User speaker, Boolean participated) {
        return eventDAO.listWithPaginationSpeaker(page, limit, sortType, sortDirection, speaker, participated);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPaginationOridnaryUser(int page, int limit, EventDAO.SortType sortType,
                                                                       EventDAO.SortDirection sortDirection,
                                                                       User ordinaryUser, Boolean participated) {
        return eventDAO.listWithPaginationOrdinaryUser(page, limit, sortType, sortDirection, ordinaryUser, participated);
    }

    @Override
    public EventDAO.ListWithCountResult listWithPaginationReportStatusFilter(int page,
                                                                             int limit, EventDAO.SortType sortType,
                                                                             EventDAO.SortDirection sortDirection,
                                                                             Report.Status reportStatus) {
        return eventDAO.listWithPaginationReportStatusFilter(page, limit, sortType, sortDirection,
                reportStatus);
    }
}
