package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/event/register")
public class EventRegisterAction implements ControllerAction {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));
        User user = (User) req.getSession().getAttribute("user");
        boolean register = Boolean.parseBoolean(req.getParameter("register"));

        eventService.registerUser(eventID, user, register);

        return req.getContextPath() + "/pages/event/view?eventID=" + eventID;
    }
}
