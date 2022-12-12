package com.nextvoyager.conferences.controller;

import com.nextvoyager.conferences.dao.event.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/event-list-sort")
public class SortEventList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderTypeParam = req.getParameter("orderType");

        EventDAO.OrderType orderType = EventDAO.OrderType.Date;
        if (orderTypeParam != null) {
            orderType = EventDAO.OrderType.valueOf(orderTypeParam);
        }

        req.getSession().setAttribute("eventListOrderType", orderType);
        resp.sendRedirect("home");
    }
}
