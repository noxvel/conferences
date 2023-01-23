package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.*;

//("/event/register")
public class EventRegisterUserAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_EVENT_ID, REGEXP_ID),
            new ValidateObject(PARAM_EVENT_REGISTER_USER, REGEXP_BOOLEAN),
    };

    private final EventService eventService;

    public EventRegisterUserAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);

        Integer eventID = Integer.valueOf(req.getParameter(PARAM_EVENT_ID));
        boolean register = Boolean.parseBoolean(req.getParameter(PARAM_EVENT_REGISTER_USER));

        User user = (User) req.getSession().getAttribute("user");

        eventService.registerUser(eventID, user, register);

        return PREFIX_PATH + "/event/view?eventID=" + eventID;
    }
}
