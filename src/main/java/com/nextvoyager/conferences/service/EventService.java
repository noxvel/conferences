package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

public interface EventService {

    Event find(Integer eventID);

    void update(Event event);

    boolean isUserRegisterEvent(Event event, User user);

    void create(Event event);

    void registerUser(Integer eventID, User user, boolean register);

    List<Event> list(EventDAO.SortType sortType, EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter);

    EventDAO.ListWithCountResult listWithPagination(Integer page, Integer limit, EventDAO.SortType sortType,
                                                    EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter);

    EventDAO.ListWithCountResult listWithPaginationSpeaker(int page, int limit, EventDAO.SortType sortType,
                                                                       EventDAO.SortDirection sortDirection,
                                                           EventDAO.TimeFilter timeFilter, User speaker, Boolean participated);

    EventDAO.ListWithCountResult listWithPaginationReportStatusFilter(int page, int limit,
                                                                      EventDAO.SortType eventListSortType,
                                                                      EventDAO.SortDirection eventListSortDirection,
                                                                      EventDAO.TimeFilter timeFilter,
                                                                      Report.Status reportStatus);

    EventDAO.ListWithCountResult listWithPaginationOridnaryUser(int page, int limit, EventDAO.SortType eventListSortType,
                                                                EventDAO.SortDirection eventListSortDirection,
                                                                EventDAO.TimeFilter timeFilter, User currentUser,
                                                                Boolean showEventParticipated);
}
