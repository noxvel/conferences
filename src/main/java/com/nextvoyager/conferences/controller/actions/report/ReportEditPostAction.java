package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_REPORT_DESCRIPTION;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_REPORT_STATUS;

//("/report/edit")
public class ReportEditPostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_REPORT_ID, REGEXP_ID),
            new ValidateObject(PARAM_REPORT_TOPIC),
            new ValidateObject(PARAM_REPORT_DESCRIPTION,true)
    };

    private static final ValidateObject[] validateObjectsModerator = {
            new ValidateObject(PARAM_REPORT_ID, REGEXP_ID),
            new ValidateObject(PARAM_REPORT_TOPIC),
            new ValidateObject(PARAM_REPORT_DESCRIPTION,true),
            new ValidateObject(PARAM_REPORT_SPEAKER, REGEXP_ID),
            new ValidateObject(PARAM_REPORT_STATUS, REGEXP_REPORT_STATUS)
    };

    private final ReportService reportService;

    public ReportEditPostAction(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        User currentUser = (User) req.getSession().getAttribute("user");

        if (currentUser.getRole() == User.Role.SPEAKER) {
            ParameterValidator.validate(req,validateObjects);
        } else {
            ParameterValidator.validate(req,validateObjectsModerator);
        }

        Integer reportID = Integer.valueOf(req.getParameter(PARAM_REPORT_ID));
        String topicParam = req.getParameter(PARAM_REPORT_TOPIC);
        String descriptionParam = req.getParameter(PARAM_REPORT_DESCRIPTION);
        String speakerParam = req.getParameter(PARAM_REPORT_SPEAKER);
        String statusParam = req.getParameter(PARAM_REPORT_STATUS);

        Report report = reportService.find(reportID);

        report.setTopic(topicParam);
        report.setDescription(descriptionParam);

        // Choose approval action for the current report
        String approvalAction = null;
        User approvalSpeaker = null;
        if (currentUser.getRole() == User.Role.MODERATOR) {
            if (!speakerParam.equals("0")) {
                approvalSpeaker = new User(Integer.valueOf(speakerParam));
                Report.Status status = Report.Status.valueOf(statusParam);
                switch (status) {
                    case CONFIRMED:
                        approvalAction = "consolidate-report-moderator";
                        break;
                    case PROPOSE_TO_SPEAKER:
                        approvalAction = "propose-to-speaker-moderator";
                        break;
                    default:
                        approvalAction = "no-approval-action";
                        break;
                }
            } else {
                approvalAction = "set-free-report-moderator";
            }
        }

        reportService.update(approvalAction, report, approvalSpeaker);

        return PREFIX_PATH + "/report/view?reportID=" + report.getId();
    }
}
