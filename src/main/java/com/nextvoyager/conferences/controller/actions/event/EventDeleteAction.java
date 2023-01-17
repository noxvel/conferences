package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//("/event/delete")
public class EventDeleteAction implements ControllerAction {

    private final EventService eventService;
    public EventDeleteAction(EventService eventService){
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        Event event = eventService.find(eventID);
        eventService.delete(event);

        return PREFIX_PATH + HOME;
    }

}
