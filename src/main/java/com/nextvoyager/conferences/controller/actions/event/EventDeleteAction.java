package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

/**
 * Delete event by id.
 * Path "/event/delete".
 *
 * @author Stanislav Bozhevskyi
 */
public class EventDeleteAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_EVENT_ID, REGEXP_ID),
    };

    private final EventService eventService;
    public EventDeleteAction(EventService eventService){
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);
        Integer eventID = Integer.valueOf(req.getParameter(PARAM_EVENT_ID));

        Event event = eventService.find(eventID);
        eventService.delete(event);

        return PREFIX_PATH + HOME;
    }

}
