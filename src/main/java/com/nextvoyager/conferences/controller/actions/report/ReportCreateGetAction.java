package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_EVENT_ID;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

/**
 * Create new report.
 * Path "/report/create".
 * GET Method
 *
 * @author Stanislav Bozhevskyi
 */

public class ReportCreateGetAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_EVENT_ID, REGEXP_ID),
    };

    private final UserService userService;

    public ReportCreateGetAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ParameterValidator.validate(req,validateObjects);
        Integer eventID = Integer.valueOf(req.getParameter(PARAM_EVENT_ID));

        Report.Status[] statuses;
        List<User> speakers;

        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser.getRole() == User.Role.SPEAKER) {
            speakers = List.of(currentUser);
            statuses = new Report.Status[]{Report.Status.OFFERED_BY_SPEAKER};
        } else {
            speakers = userService.listWithOneRole(User.Role.SPEAKER);
            statuses = Report.Status.values();
        }

        req.setAttribute("statuses", statuses);
        req.setAttribute("speakers", speakers);
        req.setAttribute("eventID", eventID);
        return REPORT_CREATE;
    }

}
