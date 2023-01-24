package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.util.validation.ValidateRegExp.*;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;

/**
 * Create new report
 * Path "/report/create".
 * POST Method
 *
 * @author Stanislav Bozhevskyi
 */
public class ReportCreatePostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_REPORT_TOPIC),
            new ValidateObject(PARAM_REPORT_EVENT, REGEXP_ID),
            new ValidateObject(PARAM_REPORT_DESCRIPTION,true)
    };

    private static final ValidateObject[] validateObjectsModerator = {
            new ValidateObject(PARAM_REPORT_TOPIC),
            new ValidateObject(PARAM_REPORT_SPEAKER, REGEXP_ID),
            new ValidateObject(PARAM_REPORT_EVENT, REGEXP_ID),
            new ValidateObject(PARAM_REPORT_STATUS, REGEXP_REPORT_STATUS),
            new ValidateObject(PARAM_REPORT_DESCRIPTION,true)
    };

    private final ReportService reportService;

    public ReportCreatePostAction(ReportService reportService) {
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

        String topicParam = req.getParameter(PARAM_REPORT_TOPIC);
        String speakerParam = req.getParameter(PARAM_REPORT_SPEAKER);
        String eventParam = req.getParameter(PARAM_REPORT_EVENT);
        String statusParam = req.getParameter(PARAM_REPORT_STATUS);
        String descriptionParam = req.getParameter(PARAM_REPORT_DESCRIPTION);

        Report report = new Report();
        report.setTopic(topicParam);
        report.setEvent(new Event(Integer.valueOf(eventParam)));
        report.setDescription(descriptionParam);

        // Choose approval action for the new report
        String approvalAction = null;
        User approvalSpeaker = null;
        if (currentUser.getRole() == User.Role.SPEAKER) {
            approvalAction = "offer-report-speaker";
            approvalSpeaker =  currentUser;
        }else{
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

        reportService.create(approvalAction, report, approvalSpeaker);

        return PREFIX_PATH + "/report/view?reportID=" + report.getId();
    }

}
