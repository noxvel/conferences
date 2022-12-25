package com.nextvoyager.conferences.controller.event;

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

        req.getSession().setAttribute("filterByEventParticipated", showEventParticipatedParam != null);
        resp.sendRedirect("home");
    }

}
