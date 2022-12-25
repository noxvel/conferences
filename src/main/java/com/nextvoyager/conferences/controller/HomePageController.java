package com.nextvoyager.conferences.controller;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/home")
public class HomePageController extends HttpServlet {

    EventService eventService = EventServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");

        // Default values for list of events
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        EventDAO.ListWithCountResult countAndList;

        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                        .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                        .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        Boolean showEventParticipated = Optional.ofNullable((Boolean) currentSession
                .getAttribute("filterByEventParticipated")).orElse(Boolean.FALSE);

        User currentUser = (User) currentSession.getAttribute("user");
        if (currentUser != null) {
            if (currentUser.getRole() == User.Role.MODERATOR) {
                countAndList = eventService.listWithPagination(page, limit, eventListSortType, eventListSortDirection);
            } else if (currentUser.getRole() == User.Role.SPEAKER) {
                countAndList = eventService.listWithPaginationSpeaker(page, limit, eventListSortType,
                        eventListSortDirection, currentUser, showEventParticipated);
            } else if (currentUser.getRole() == User.Role.ORDINARY_USER) {
                countAndList = eventService.listWithPaginationOridnaryUser(page, limit, eventListSortType,
                        eventListSortDirection, currentUser, showEventParticipated);
            } else {
                countAndList = eventService.listWithPaginationReportStatusFilter(page, limit, eventListSortType,
                        eventListSortDirection, Report.Status.CONFIRMED);
            }
        } else {
            countAndList = eventService.listWithPaginationReportStatusFilter(page, limit, eventListSortType,
                    eventListSortDirection, Report.Status.CONFIRMED);
        }
        //------------------------------------------------------------------------------------------------

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("events", countAndList.getList());
        req.setAttribute("page", page);
        req.setAttribute("sortType", eventListSortType);
        req.setAttribute("sortDirection", eventListSortDirection);
        req.setAttribute("showEventParticipated", showEventParticipated);
        req.setAttribute("numOfPages", numOfPages);
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req,resp);


    }

}
