package com.nextvoyager.conferences.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/event-list-filter")
public class FilterEventList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String showSpeakerEventParticipatedParam = req.getParameter("showInWhichParticipated");

        req.getSession().setAttribute("filterBySpeakerParticipated", showSpeakerEventParticipatedParam != null);
        resp.sendRedirect("home");
    }

}
