package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/report/create")
public class ReportCreateController extends HttpServlet {

    private final ReportService reportService = AppContext.getInstance().getReportService();
    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        Report.Status[] statuses;
        List<User> speakers;

        HttpSession session = req.getSession(false);
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
        req.getRequestDispatcher("/WEB-INF/jsp/report/report-create.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String topicParam = req.getParameter("topic");
        String speakerParam = req.getParameter("speaker");
        String eventParam = req.getParameter("event");
        String statusParam = req.getParameter("status");
        String descriptionParam = req.getParameter("description");

        // TODO: 01.12.2022 create validation to input data

        User currentUser = (User) req.getSession().getAttribute("user");

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

        resp.sendRedirect("view?reportID=" + report.getId());
    }
}
