package com.nextvoyager.conferences.controller.actions;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

//("/home")
public class HomePageAction implements ControllerAction {

    private final EventService eventService;

    public HomePageAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String pageParam = req.getParameter("page");

        // Default values for list of events
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        ListWithCount<Event> countAndList;

        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                        .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                        .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        Boolean showEventParticipated = Optional.ofNullable((Boolean) currentSession
                .getAttribute("filterByEventParticipated")).orElse(Boolean.FALSE);

        User currentUser = (User) currentSession.getAttribute("user");
        if (currentUser != null) {
            if (currentUser.getRole() == User.Role.MODERATOR) {
                countAndList = eventService.listWithPagination(page, limit, eventListSortType, eventListSortDirection, eventTimeFilter);
            } else if (currentUser.getRole() == User.Role.SPEAKER) {
                countAndList = eventService.listWithPaginationSpeaker(page, limit, eventListSortType,
                        eventListSortDirection, eventTimeFilter, currentUser, showEventParticipated);
            } else if (currentUser.getRole() == User.Role.ORDINARY_USER) {
                countAndList = eventService.listWithPaginationOridnaryUser(page, limit, eventListSortType,
                        eventListSortDirection, eventTimeFilter, currentUser, showEventParticipated);
            } else {
                countAndList = eventService.listWithPaginationReportStatusFilter(page, limit, eventListSortType,
                        eventListSortDirection, eventTimeFilter, Report.Status.CONFIRMED);
            }
        } else {
            countAndList = eventService.listWithPaginationReportStatusFilter(page, limit, eventListSortType,
                    eventListSortDirection, eventTimeFilter, Report.Status.CONFIRMED);
        }
        //------------------------------------------------------------------------------------------------

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("events", countAndList.getList());
        req.setAttribute("page", page);
        req.setAttribute("sortType", eventListSortType);
        req.setAttribute("sortDirection", eventListSortDirection);
        req.setAttribute("showEventParticipated", showEventParticipated);
        req.setAttribute("eventTimeFilter", eventTimeFilter);
        req.setAttribute("numOfPages", numOfPages);

        return HOME;
    }

}
