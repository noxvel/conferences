package com.nextvoyager.conferences.controller.actions.user.speaker;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_REPORT_ID;
import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_APPROVAL_ACTION;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

//("/speaker-report-action")
public class SpeakerReportApprovalAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_REPORT_ID, REGEXP_ID),
            new ValidateObject(PARAM_USER_APPROVAL_ACTION)
    };

    private final ReportService reportService;

    public SpeakerReportApprovalAction(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);
        Integer reportID = Integer.valueOf(req.getParameter(PARAM_REPORT_ID));
        String actionParam = req.getParameter(PARAM_USER_APPROVAL_ACTION);

        User speaker = (User) req.getSession().getAttribute("user");

        Report report = reportService.find(reportID);
        reportService.update(actionParam, report, speaker);

        return PREFIX_PATH + "/report/view?reportID=" + reportID;
    }
}
