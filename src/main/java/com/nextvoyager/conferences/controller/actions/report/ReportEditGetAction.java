package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_REPORT_ID;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

/**
 * Edit existing report.
 * Path "/report/edit".
 * GET Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class ReportEditGetAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_REPORT_ID, REGEXP_ID),
    };

    private final ReportService reportService;
    private final UserService userService;

    public ReportEditGetAction(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);
        Integer reportID = Integer.valueOf(req.getParameter(PARAM_REPORT_ID));

        List<User> speakers = userService.listWithOneRole(User.Role.SPEAKER);

        Report report = reportService.find(reportID);

        req.setAttribute("report", report);
        req.setAttribute("statuses", Report.Status.values());
        req.setAttribute("speakers", speakers);
        return REPORT_EDIT;
    }

}
