package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.notification.EventNotificationManager;

import java.util.List;

/**
 * The class which implements {@link EventService} interface
 *
 * @author Stanislav Bozhevskyi
 */
public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;
    private final UserDAO userDAO;

    public EventServiceImpl(EventDAO eventDAO, UserDAO userDAO) {
        this.eventDAO = eventDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void create(Event event) {
        eventDAO.create(event);
    }

    @Override
    public void update(Event event, boolean sendNotification) {
        eventDAO.update(event);
        if (sendNotification) {
            List<User> userList = userDAO.receiveEventNotificationsList(event);
            EventNotificationManager.notify(userList, event, EventNotificationManager.NotificationType.UPDATE);
        }
    }

    @Override
    public void delete(Event event) {
//        List<User> userList = userDAO.receiveEventNotificationsList(event);
        eventDAO.delete(event);
//        EventNotificationManager.notify(userList, event, EventNotificationManager.NotificationType.DELETE);
    }

    public Event find(Integer id) {
        return eventDAO.find(id);
    }

    @Override
    public void registerUser(Event event, User user, boolean register) {
        if (event != null) {
            eventDAO.registerUser(event,user,register);
        }
    }

    @Override
    public boolean isUserRegisterEvent(Event event, User user) {
        return eventDAO.isUserRegisterEvent(event, user);
    }

    @Override
    public List<Event> list(EventDAO.SortType sortType, EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter) {
        return eventDAO.list(sortType, sortDirection, timeFilter);
    }

    @Override
    public ListWithCount<Event> listWithPagination(Integer page, Integer limit, EventDAO.SortType sortType,
                                                   EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                   User currentUser, Boolean participated) {

        ListWithCount<Event> countAndList;

        if (currentUser != null) {
            if (currentUser.getRole() == User.Role.MODERATOR) {
                countAndList = listWithPaginationCommon(page, limit, sortType,
                        sortDirection, timeFilter);
            } else if (currentUser.getRole() == User.Role.SPEAKER) {
                countAndList = listWithPaginationSpeaker(page, limit, sortType,
                        sortDirection, timeFilter, currentUser, participated);
            } else if (currentUser.getRole() == User.Role.ORDINARY_USER) {
                countAndList = listWithPaginationOrdinaryUser(page, limit, sortType,
                        sortDirection, timeFilter, currentUser, participated);
            } else {
                countAndList = listWithPaginationReportStatusFilter(page, limit, sortType,
                        sortDirection, timeFilter, Report.Status.CONFIRMED);
            }
        } else {
            countAndList = listWithPaginationReportStatusFilter(page, limit, sortType,
                    sortDirection, timeFilter, Report.Status.CONFIRMED);
        }
        return countAndList;
    }

    @Override
    public ListWithCount<Event> listWithPaginationCommon(Integer page, Integer limit, EventDAO.SortType sortType,
                                                   EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter) {
        return eventDAO.listWithPagination(page, limit, sortType, sortDirection, timeFilter);
    }

    @Override
    public ListWithCount<Event> listWithPaginationSpeaker(int page, int limit, EventDAO.SortType sortType,
                                                                             EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                                  User speaker, Boolean participated) {
        return eventDAO.listWithPaginationSpeaker(page, limit, sortType, sortDirection, timeFilter, speaker, participated);
    }

    @Override
    public ListWithCount<Event> listWithPaginationOrdinaryUser(int page, int limit, EventDAO.SortType sortType,
                                                                       EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                                       User ordinaryUser, Boolean participated) {
        return eventDAO.listWithPaginationOrdinaryUser(page, limit, sortType, sortDirection, timeFilter, ordinaryUser, participated);
    }

    @Override
    public ListWithCount<Event> listWithPaginationReportStatusFilter(int page,
                                                                             int limit, EventDAO.SortType sortType,
                                                                             EventDAO.SortDirection sortDirection, EventDAO.TimeFilter timeFilter,
                                                                             Report.Status reportStatus) {
        return eventDAO.listWithPaginationReportStatusFilter(page, limit, sortType, sortDirection,timeFilter, reportStatus);
    }
}
