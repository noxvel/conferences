package com.nextvoyager.conferences.controller.actions.user.moderator;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//("/moderator-report-action")
public class ModeratorReportApprovalAction implements ControllerAction {

    private final ReportService reportService;

    public ModeratorReportApprovalAction(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));
        String actionParam = req.getParameter("action");

        Report report = reportService.find(reportID);
        reportService.update(actionParam, report, report.getSpeaker());

        return PREFIX_PATH + "/report/view?reportID=" + reportID;
    }
}
