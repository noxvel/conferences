package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//("/event/create")
public class EventCreatePostAction implements ControllerAction {

    private final EventService eventService;

    public EventCreatePostAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameParam = req.getParameter("name");
        String placeParam = req.getParameter("place");
        String beginDateParam = req.getParameter("beginDate");
        String endDateParam = req.getParameter("endDate");
        String descriptionParam = req.getParameter("description");

        validate(nameParam,placeParam,beginDateParam,endDateParam,descriptionParam);

        Event event = new Event();
        event.setName(nameParam);
        event.setPlace(placeParam);
        event.setBeginDate(LocalDateTime.parse(beginDateParam));
        event.setEndDate(LocalDateTime.parse(endDateParam));
        event.setDescription(descriptionParam);

        eventService.create(event);

        return req.getContextPath() + "/pages/event/view?eventID=" + event.getId();
    }

    private void validate(String name, String place, String beginDate, String endDate, String description) throws ServletException{

        List<String> errorMessages = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            errorMessages.add("Please enter name");
        }
        if (place == null || place.trim().isEmpty()) {
            errorMessages.add("Please enter place");
        }
        if (beginDate == null || beginDate.trim().isEmpty()) {
            errorMessages.add("Please enter begin date");
        }
        if (endDate == null || endDate.trim().isEmpty()) {
            errorMessages.add("Please enter end date");
        }
        if (description == null) {
            errorMessages.add("Please enter description");
        }

        if (!errorMessages.isEmpty()) {
            throw new ServletException("Invalid input values: " + String.join(",", errorMessages));
        }

    }

}
