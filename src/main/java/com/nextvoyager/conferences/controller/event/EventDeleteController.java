package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@WebServlet("/event/delete")
public class EventDeleteController extends HttpServlet {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        Event event = eventService.find(eventID);
        eventService.delete(event);

        resp.sendRedirect(req.getContextPath() + "/home");
    }

}
