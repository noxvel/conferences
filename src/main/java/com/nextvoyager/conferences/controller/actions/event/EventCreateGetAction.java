package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Create new event.
 * Path "/event/create".
 * GET method.
 *
 * @author Stanislav Bozhevskyi
 */
public class EventCreateGetAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return EVENT_CREATE;
    }

}
