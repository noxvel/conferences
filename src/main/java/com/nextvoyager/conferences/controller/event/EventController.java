package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.event.EventDAO;
import com.nextvoyager.conferences.model.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/event/view")
public class EventController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain UserDAO.
        EventDAO eventDAO = javabase.getEventDAO();

        Event event = eventDAO.find(eventID);

        req.setAttribute("event", event);
        req.getRequestDispatcher("event-view.jsp").forward(req,resp);
    }
}
