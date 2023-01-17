package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//("/event/register")
public class EventRegisterUserAction implements ControllerAction {

    private final EventService eventService;

    public EventRegisterUserAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));
        User user = (User) req.getSession().getAttribute("user");
        boolean register = Boolean.parseBoolean(req.getParameter("register"));

        eventService.registerUser(eventID, user, register);

        return PREFIX_PATH + "/event/view?eventID=" + eventID;
    }
}
