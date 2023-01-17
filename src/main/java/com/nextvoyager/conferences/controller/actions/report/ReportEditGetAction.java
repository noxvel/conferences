package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

//("/report/edit")
public class ReportEditGetAction implements ControllerAction {

    private final ReportService reportService;
    private final UserService userService;

    public ReportEditGetAction(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));

        List<User> speakers = userService.listWithOneRole(User.Role.SPEAKER);

        Report report = reportService.find(reportID);

        req.setAttribute("report", report);
        req.setAttribute("statuses", Report.Status.values());
        req.setAttribute("speakers", speakers);
        return REPORT_EDIT;
    }

}
