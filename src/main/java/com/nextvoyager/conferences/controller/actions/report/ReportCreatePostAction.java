package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

//("/report/create")
public class ReportCreatePostAction implements ControllerAction {

    private final ReportService reportService;
    private final UserService userService;

    public ReportCreatePostAction(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        String topicParam = req.getParameter("topic");
        String speakerParam = req.getParameter("speaker");
        String eventParam = req.getParameter("event");
        String statusParam = req.getParameter("status");
        String descriptionParam = req.getParameter("description");

        User currentUser = (User) req.getSession().getAttribute("user");

        if (currentUser.getRole() == User.Role.SPEAKER) {
            validate(topicParam,eventParam,descriptionParam);
        } else {
            validate(topicParam, eventParam, descriptionParam, speakerParam, statusParam);
        }

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

        reportService.create(approvalAction, report, approvalSpeaker);

        return PREFIX_PATH + "/report/view?reportID=" + report.getId();
    }

    private void validate(String topic, String event, String description) throws ServletException{

        List<String> errorMessages = new ArrayList<>();

        if (topic == null || topic.trim().isEmpty()) {
            errorMessages.add("Please enter topic");
        }
        if (event == null || event.trim().isEmpty()) {
            errorMessages.add("Please enter event");
        }
        if (description == null) {
            errorMessages.add("Please enter description");
        }

        if (!errorMessages.isEmpty()) {
            throw new ServletException("Invalid input values: " + String.join(",", errorMessages));
        }

    }
    private void validate(String topic, String event, String description, String speaker, String status) throws ServletException{

        List<String> errorMessages = new ArrayList<>();

        if (topic == null || topic.trim().isEmpty()) {
            errorMessages.add("Please enter topic");
        }
        if (speaker == null) {
            errorMessages.add("Please enter speaker");
        }
        if (event == null || event.trim().isEmpty()) {
            errorMessages.add("Please enter event");
        }
        if (status == null || status.trim().isEmpty()) {
            errorMessages.add("Please enter status");
        }
        if (description == null) {
            errorMessages.add("Please enter description");
        }

        if (!errorMessages.isEmpty()) {
            throw new ServletException("Invalid input values: " + String.join(",", errorMessages));
        }

    }
}
