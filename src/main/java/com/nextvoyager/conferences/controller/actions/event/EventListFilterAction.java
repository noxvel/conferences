package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Set filter for list of events.
 * Path "/event/list-filter".
 *
 * @author Stanislav Bozhevskyi
 */
public class EventListFilterAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        String showEventParticipatedParam = req.getParameter("showInWhichParticipated");
        String timeFilterParam = req.getParameter("timeFilter");
        String redirectPath = req.getParameter("redirectPath");

        req.getSession().setAttribute("filterByEventParticipated", showEventParticipatedParam != null);
        req.getSession().setAttribute("eventTimeFilter", EventDAO.TimeFilter.valueOf(timeFilterParam));
        return PREFIX_PATH + redirectPath;
    }

}
