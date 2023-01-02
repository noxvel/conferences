package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/event/edit")
public class EventEditControllerGet implements ControllerAction {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        Event event = eventService.find(eventID);

        req.setAttribute("event", event);
//        req.getRequestDispatcher("/WEB-INF/jsp/event/edit.jsp").forward(req,resp);
        return "event/edit";

    }

}
