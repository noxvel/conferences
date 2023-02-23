package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Set type of represent for list of events.
 * Path "/event/list-view-form".
 *
 * @author Stanislav Bozhevskyi
 */
public class EventListViewFormAction implements ControllerAction {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String viewFormParam = req.getParameter("viewForm");
//        String redirectPath = req.getParameter("redirectPath");

        EventListViewForm viewForm = EventListViewForm.BLOCK;
        if (viewFormParam != null) {
            viewForm = EventListViewForm.valueOf(viewFormParam);
        }

        req.getSession().setAttribute("eventListViewForm", viewForm);
        return PREFIX_PATH + HOME;
    }

    /**
     * Type of view for list of events.
     * Path "/event/list-sort".
     *
     * @author Stanislav Bozhevskyi
     */
    public enum EventListViewForm {
        BLOCK,
        LIST
    }
}
