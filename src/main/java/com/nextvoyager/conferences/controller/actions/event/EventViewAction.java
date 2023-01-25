package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.PaginationUtil;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_EVENT_ID;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

/**
 * View existing event.
 * Path "/event/view".
 *
 * @author Stanislav Bozhevskyi
 */
public class EventViewAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_EVENT_ID, REGEXP_ID),
    };

    private final EventService eventService;
    private final ReportService reportService;
    private final UserService userService;

    public EventViewAction(EventService eventService, ReportService reportService, UserService userService) {
        this.eventService = eventService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = PaginationUtil.handlePaginationPageParameter(req);
        int limit = PaginationUtil.handlePaginationLimitParameter(req);

        ParameterValidator.validate(req,validateObjects);
        Integer eventID = Integer.valueOf(req.getParameter(PARAM_EVENT_ID));

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
            countAndList = reportService.listWithPagination(page, limit, event, Report.Status.CONFIRMED);
        }  else {
            reportStatusFilter = Optional.ofNullable((Report.Status) session.getAttribute("reportStatusFilter"));
            if (userRole == User.Role.SPEAKER) {
                if (reportStatusFilter.isEmpty()) {
                    countAndList = reportService.listWithPagination(page, limit, event, currentUser);
                } else {
                    Report.Status filterStatus= reportStatusFilter.get();
                    if (filterStatus == Report.Status.FREE || filterStatus == Report.Status.CONFIRMED) {
                        countAndList = reportService.listWithPagination(page, limit, event, filterStatus);
                    } else {
                        countAndList = reportService.listWithPagination(page, limit, event, currentUser, reportStatusFilter.get());
                    }
                }
            } else {
                if (reportStatusFilter.isEmpty()) {
                    countAndList = reportService.listWithPagination(page, limit, event);
                } else {
                    countAndList = reportService.listWithPagination(page, limit, event, reportStatusFilter.get());
                }
            }
        }

        int numOfPages = PaginationUtil.getNumOfPages(countAndList.getCount(),limit);

        List<Report> listOfReports = countAndList.getList();
        listOfReports.stream()
                .filter(report -> report.getSpeaker() != null)
                .forEach(report -> {
                    report.setSpeaker(userService.find(report.getSpeaker().getId()));
                });
        event.setReports(listOfReports);

        if (userRole == User.Role.MODERATOR) {
            List<User> participants = userService.listOfEventParticipants(event);
            event.setParticipants(participants);
        }

        req.setAttribute("page", page);
        req.setAttribute("limit", limit);
        req.setAttribute("reportStatusFilter", reportStatusFilter.orElse(null));
        req.setAttribute("reportStatuses", Report.Status.values());
        req.setAttribute("event", event);
        req.setAttribute("numOfPages", numOfPages);

        return EVENT_VIEW;
    }
}
