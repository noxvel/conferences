package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/event-list-filter")
public class FilterEventList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String showEventParticipatedParam = req.getParameter("showInWhichParticipated");
        String timeFilterParam = req.getParameter("timeFilter");
        String redirectPath = req.getParameter("redirectPath");

        req.getSession().setAttribute("filterByEventParticipated", showEventParticipatedParam != null);
        req.getSession().setAttribute("eventTimeFilter", EventDAO.TimeFilter.valueOf(timeFilterParam));
        resp.sendRedirect(redirectPath);
    }

}
