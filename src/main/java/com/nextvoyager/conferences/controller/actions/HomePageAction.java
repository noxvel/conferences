package com.nextvoyager.conferences.controller.actions;

import com.nextvoyager.conferences.controller.actions.event.EventListViewForm;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.PaginationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Home page controller action.
 * Path "/home".
 *
 * @author Stanislav Bozhevskyi
 */
public class HomePageAction implements ControllerAction {

    private final EventService eventService;

    public HomePageAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int page = PaginationUtil.handlePaginationPageParameter(req);
        int limit = PaginationUtil.handlePaginationLimitParameter(req);

        ListWithCount<Event> countAndList;

        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                        .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                        .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Descending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        Boolean showEventParticipated = Optional.ofNullable((Boolean) currentSession
                .getAttribute("filterByEventParticipated")).orElse(Boolean.FALSE);

        EventListViewForm eventListViewForm = Optional.ofNullable((EventListViewForm) currentSession
                .getAttribute("eventListViewForm")).orElse(EventListViewForm.BLOCK);

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

        int numOfPages = PaginationUtil.getNumOfPages(countAndList.getCount(),limit);

        req.setAttribute("page", page);
        req.setAttribute("limit", limit);
        req.setAttribute("events", countAndList.getList());
        req.setAttribute("sortType", eventListSortType);
        req.setAttribute("sortDirection", eventListSortDirection);
        req.setAttribute("showEventParticipated", showEventParticipated);
        req.setAttribute("eventTimeFilter", eventTimeFilter);
        req.setAttribute("eventListViewForm", eventListViewForm);
        req.setAttribute("numOfPages", numOfPages);

        return HOME;
    }

}
