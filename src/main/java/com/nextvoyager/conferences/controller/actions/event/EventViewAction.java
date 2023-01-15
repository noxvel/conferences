package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

//@WebServlet("/event/view")
public class EventViewAction implements ControllerAction {

    private final ReportService reportService = AppContext.getInstance().getReportService();
    private final EventService eventService = AppContext.getInstance().getEventService();
    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));
        String pageParam = req.getParameter("page");

        // Default values for list of reports
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        Event event = eventService.find(eventID);

        HttpSession session = req.getSession();
        User currentUser = null;
        User.Role userRole = null;

        if (session.getAttribute("user") != null) {
            currentUser = (User) session.getAttribute("user");
            userRole = currentUser.getRole();
            if (userRole == User.Role.ORDINARY_USER) {
                boolean isRegister = eventService.isUserRegisterEvent(event, currentUser);
                req.setAttribute("isRegister", isRegister);
            }
        }

        ListWithCount<Report> countAndList;
        Optional<Report.Status> reportStatusFilter = Optional.empty();

        // Get list of all confirmed reports for not registered or the ordinary user
        if (userRole == null || userRole == User.Role.ORDINARY_USER) {
            countAndList = reportService.listWithPagination(page, limit, eventID, Report.Status.CONFIRMED);
        }  else {
            reportStatusFilter = Optional.ofNullable((Report.Status) session.getAttribute("reportStatusFilter"));
            if (userRole == User.Role.SPEAKER) {
                if (reportStatusFilter.isEmpty()) {
                    countAndList = reportService.listWithPagination(page, limit, eventID, currentUser);
                } else {
                    Report.Status filterStatus= reportStatusFilter.get();
                    if (filterStatus == Report.Status.FREE || filterStatus == Report.Status.CONFIRMED) {
                        countAndList = reportService.listWithPagination(page, limit, eventID, filterStatus);
                    } else {
                        countAndList = reportService.listWithPagination(page, limit, eventID, currentUser, reportStatusFilter.get());
                    }
                }
            } else {
                if (reportStatusFilter.isEmpty()) {
                    countAndList = reportService.listWithPagination(page, limit, eventID);
                } else {
                    countAndList = reportService.listWithPagination(page, limit, eventID, reportStatusFilter.get());
                }
            }
        }


        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        List<Report> listOfReports = countAndList.getList();
        listOfReports.stream()
                .filter(report -> report.getSpeaker() != null)
                .forEach(report -> {
                    report.setSpeaker(userService.find(report.getSpeaker().getId()));
                });
        event.setReports(listOfReports);

        req.setAttribute("reportStatusFilter", reportStatusFilter.orElse(null));
        req.setAttribute("reportStatuses", Report.Status.values());
        req.setAttribute("event", event);
        req.setAttribute("page", page);
        req.setAttribute("numOfPages", numOfPages);

        return EVENT_VIEW;
    }
}
