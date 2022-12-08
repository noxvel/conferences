package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.event.EventDAO;
import com.nextvoyager.conferences.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.Event;
import com.nextvoyager.conferences.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/event/view")
public class EventViewController extends HttpServlet {

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

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain UserDAO.
        EventDAO eventDAO = javabase.getEventDAO();
        Event event = eventDAO.find(eventID);

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            boolean isRegister = eventDAO.isUserRegisterEvent(event, user);
            req.setAttribute("isRegister", isRegister);
        }

        ReportDAO reportDAO = javabase.getReportDAO();
//        List<Report> reports = reportDAO.list(eventID);
        ReportDAO.ListWithCountResult countAndList = reportDAO.listWithPagination(eventID, page, limit);
        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);
        event.setReports(countAndList.getList());

        req.setAttribute("event", event);
        req.setAttribute("page", page);
        req.setAttribute("numOfPages", numOfPages);

        req.getRequestDispatcher("event-view.jsp").forward(req,resp);
    }
}
