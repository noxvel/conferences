package com.nextvoyager.conferences;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;

/**
 * Application context class for easier access to service classes
 *
 * @author Stanislav Bozhevskyi
 */
public class AppContext {
    private static volatile AppContext appContext = new AppContext();

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final EventDAO eventDAO = daoFactory.getEventDAO();
    private final ReportDAO reportDAO = daoFactory.getReportDAO();
    private final UserDAO userDAO = daoFactory.getUserDAO();

    private final EventService eventService = new EventServiceImpl(eventDAO, userDAO);
    private final ReportService reportService = new ReportServiceImpl(reportDAO,userDAO);
    private final UserService userService = new UserServiceImpl(userDAO);

    public static AppContext getInstance() {
        return appContext;
    }

    public EventService getEventService() {
        return eventService;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public UserService getUserService() {
        return userService;
    }
}
