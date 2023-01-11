package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

//@WebServlet("/event/edit")
public class EventEditPostAction implements ControllerAction {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("eventID");
        String nameParam = req.getParameter("name");
        String placeParam = req.getParameter("place");
        String beginDateParam = req.getParameter("beginDate");
        String endDateParam = req.getParameter("endDate");
        String descriptionParam = req.getParameter("description");
        String participantsCameParam = req.getParameter("participantsCame");

        Event event = new Event();
        event.setId(Integer.valueOf(idParam));
        event.setName(nameParam);
        event.setPlace(placeParam);
        event.setBeginDate(LocalDateTime.parse(beginDateParam));
        event.setEndDate(LocalDateTime.parse(endDateParam));
        event.setDescription(descriptionParam);
        event.setParticipantsCame(Integer.valueOf(participantsCameParam));

        eventService.update(event);

        return req.getContextPath() + "/pages/event/view?eventID=" + event.getId();
    }
}
