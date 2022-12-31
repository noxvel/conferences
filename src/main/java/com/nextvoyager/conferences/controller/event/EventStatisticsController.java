package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/event/statistics")
public class EventStatisticsController extends HttpServlet {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");

        // Default values for list of events
        int page = 1;
        int limit = 5;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        EventDAO.ListWithCountResult countAndList = eventService.listWithPagination(page, limit, eventListSortType,
                eventListSortDirection, eventTimeFilter);

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("events", countAndList.getList());
        req.setAttribute("page", page);
        req.setAttribute("sortType", eventListSortType);
        req.setAttribute("sortDirection", eventListSortDirection);
        req.setAttribute("eventTimeFilter", eventTimeFilter);
        req.setAttribute("numOfPages", numOfPages);
        req.getRequestDispatcher("/WEB-INF/jsp/event/event-statistics.jsp").forward(req,resp);


    }

}
