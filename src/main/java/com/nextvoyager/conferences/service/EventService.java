package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

/**
 * Event service interface
 *
 * @author Stanislav Bozhevskyi
 */
public interface EventService {

    Event find(Integer eventID);

    void update(Event event, boolean sendNotification);

    void create(Event event);

    void delete(Event event);

    boolean isUserRegisterEvent(Event event, User user);

    void registerUser(Event event, User user, boolean register);

    List<Event> list(EventDAO.SortType sortType, EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter);

    ListWithCount<Event> listWithPagination(Integer page, Integer limit, EventDAO.SortType sortType,
                                            EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter);

    ListWithCount<Event> listWithPaginationSpeaker(int page, int limit, EventDAO.SortType sortType,
                                                                       EventDAO.SortDirection sortDirection,
                                                           EventDAO.TimeFilter timeFilter, User speaker, Boolean participated);

    ListWithCount<Event> listWithPaginationReportStatusFilter(int page, int limit,
                                                                      EventDAO.SortType eventListSortType,
                                                                      EventDAO.SortDirection eventListSortDirection,
                                                                      EventDAO.TimeFilter timeFilter,
                                                                      Report.Status reportStatus);

    ListWithCount<Event> listWithPaginationOridnaryUser(int page, int limit, EventDAO.SortType eventListSortType,
                                                                EventDAO.SortDirection eventListSortDirection,
                                                                EventDAO.TimeFilter timeFilter, User currentUser,
                                                                Boolean showEventParticipated);

}
