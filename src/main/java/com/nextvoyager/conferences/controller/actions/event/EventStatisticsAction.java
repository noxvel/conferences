package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.PaginationUtil;
import com.nextvoyager.conferences.util.filecreator.ExportFileFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

//("/event/statistics")
public class EventStatisticsAction implements ControllerAction {

    private final EventService eventService;

    public EventStatisticsAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int page = PaginationUtil.handlePaginationPageParameter(req);
        int limit = PaginationUtil.handlePaginationLimitParameter(req, 12);

        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        ListWithCount<Event> countAndList = eventService.listWithPagination(page, limit, eventListSortType,
                eventListSortDirection, eventTimeFilter);

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("page", page);
        req.setAttribute("limit", limit);
        req.setAttribute("events", countAndList.getList());
        req.setAttribute("numOfPages", numOfPages);
        req.setAttribute("fileFormats", ExportFileFormat.values());
        req.setAttribute("sortType", eventListSortType);
        req.setAttribute("sortDirection", eventListSortDirection);
        req.setAttribute("eventTimeFilter", eventTimeFilter);
        return EVENT_STATISTICS;

    }

}
