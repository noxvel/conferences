package com.nextvoyager.conferences.controller.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@WebServlet("/event/edit")
public class EventEditController extends HttpServlet {

    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        Event event = eventService.find(eventID);

        req.setAttribute("event", event);
        req.getRequestDispatcher("/WEB-INF/jsp/event/event-edit.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        resp.sendRedirect("view?eventID=" + event.getId());
    }
}
