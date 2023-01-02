package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/event-list-sort")
public class SortEventList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sortTypeParam = req.getParameter("sortType");
        String sortDirectionParam = req.getParameter("sortDirection");
        String redirectPath = req.getParameter("redirectPath");

        EventDAO.SortType sortType = EventDAO.SortType.Date;
        if (sortTypeParam != null) {
            sortType = EventDAO.SortType.valueOf(sortTypeParam);
        }
        EventDAO.SortDirection sortDirection = EventDAO.SortDirection.Ascending;
        if (sortDirectionParam != null) {
            sortDirection = EventDAO.SortDirection.valueOf(sortDirectionParam);
        }

        req.getSession().setAttribute("eventListSortType", sortType);
        req.getSession().setAttribute("eventListSortDirection", sortDirection);
        resp.sendRedirect(redirectPath);
    }
}
