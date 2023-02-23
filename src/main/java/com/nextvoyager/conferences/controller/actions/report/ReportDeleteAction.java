package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_REPORT_ID;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

/**
 * Delete existing event.
 * Path "/report/delete".
 *
 * @author Stanislav Bozhevskyi
 */
public class ReportDeleteAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_REPORT_ID, REGEXP_ID),
    };

    private final ReportService reportService;

    public ReportDeleteAction(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ParameterValidator.validate(req,validateObjects);
        Integer reportID = Integer.valueOf(req.getParameter(PARAM_REPORT_ID));

        Report report = reportService.find(reportID);
        reportService.delete(report);

        return PREFIX_PATH + "/event/view?eventID=" + report.getEvent().getId();
    }

}
