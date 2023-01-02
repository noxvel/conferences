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
import java.time.Instant;
import java.util.Date;

//@WebServlet("/event/edit")
public class EventEditControllerPost implements ControllerAction {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("eventID");
        String nameParam = req.getParameter("name");
        String placeParam = req.getParameter("place");
        String beginDateParam = req.getParameter("beginDateISO");
        String endDateParam = req.getParameter("endDateISO");
        String descriptionParam = req.getParameter("description");
        String participantsCameParam = req.getParameter("participantsCame");

        // TODO: 01.12.2022 create validation to input data

        Event event = new Event();
        event.setId(Integer.valueOf(idParam));
        event.setName(nameParam);
        event.setPlace(placeParam);
        event.setBeginDate(Date.from(Instant.parse(beginDateParam)));
        event.setEndDate(Date.from(Instant.parse(endDateParam)));
        event.setDescription(descriptionParam);
        event.setParticipantsCame(Integer.valueOf(participantsCameParam));

        eventService.update(event);

//        resp.sendRedirect("view?eventID=" + event.getId());
        return req.getContextPath() + "/pages/event/view?eventID=" + event.getId();
    }
}
