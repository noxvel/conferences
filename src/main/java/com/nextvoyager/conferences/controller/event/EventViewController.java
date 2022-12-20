package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/event/view")
public class EventViewController extends HttpServlet {

    EventService eventService = EventServiceImpl.getInstance();
    ReportService reportService = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));
        String pageParam = req.getParameter("page");

        // Default values for list of reports
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        Event event = eventService.find(eventID);

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (user.getRole() == User.Role.USER) {
                boolean isRegister = eventService.isUserRegisterEvent(event, user);
                req.setAttribute("isRegister", isRegister);
            }
        }

        ReportDAO.ListWithCountResult countAndList;

        HttpSession currentSession = req.getSession();
        Optional<Report.Status> reportStatusFilter = Optional.ofNullable((Report.Status) currentSession.getAttribute("reportStatusFilter"));
        if (reportStatusFilter.isEmpty()) {
            countAndList = reportService.listWithPagination(eventID, page, limit);
        } else {
            countAndList = reportService.listWithPagination(eventID, page, limit, reportStatusFilter.get());
        }

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);
        event.setReports(countAndList.getList());

        req.setAttribute("reportStatusFilter", reportStatusFilter.orElse(null));
        req.setAttribute("reportStatuses", Report.Status.values());
        req.setAttribute("event", event);
        req.setAttribute("page", page);
        req.setAttribute("numOfPages", numOfPages);

        req.getRequestDispatcher("/WEB-INF/jsp/event/event-view.jsp").forward(req,resp);
    }
}
