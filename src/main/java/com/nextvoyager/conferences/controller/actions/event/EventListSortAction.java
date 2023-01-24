package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Set sort type and direction for list of events.
 * Path "/event/list-sort".
 *
 * @author Stanislav Bozhevskyi
 */
public class EventListSortAction implements ControllerAction {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String sortTypeParam = req.getParameter("sortType");
        String sortDirectionParam = req.getParameter("sortDirection");
        String redirectPath = req.getParameter("redirectPath");

        EventDAO.SortType sortType = EventDAO.SortType.Date;
        if (sortTypeParam != null) {
            sortType = EventDAO.SortType.valueOf(sortTypeParam);
        }
        EventDAO.SortDirection sortDirection = EventDAO.SortDirection.Ascending;
        if (sortDirectionParam != null) {
            sortDirection = EventDAO.SortDirection.valueOf(sortDirectionParam);
        }

        req.getSession().setAttribute("eventListSortType", sortType);
        req.getSession().setAttribute("eventListSortDirection", sortDirection);
        return PREFIX_PATH + redirectPath;
    }
}
