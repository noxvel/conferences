package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//("/report/edit")
public class ReportEditPostAction implements ControllerAction {

    private final ReportService reportService;

    public ReportEditPostAction(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));
        String topicParam = req.getParameter("topic");
        String speakerParam = req.getParameter("speaker");
        String eventParam = req.getParameter("event");
        String statusParam = req.getParameter("status");
        String descriptionParam = req.getParameter("description");

        User currentUser = (User) req.getSession().getAttribute("user");

        Report report = reportService.find(reportID);

        report.setTopic(topicParam);
        report.setDescription(descriptionParam);

        // Choose approval action for the current report
        String approvalAction = null;
        User approvalSpeaker = null;
        if (currentUser.getRole() == User.Role.MODERATOR) {
            if (!speakerParam.equals("")) {
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
