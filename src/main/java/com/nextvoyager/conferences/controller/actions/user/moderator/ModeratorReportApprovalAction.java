package com.nextvoyager.conferences.controller.actions.user.moderator;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/moderator-report-action")
public class ModeratorReportApprovalAction implements ControllerAction {

    private final ReportService reportService = AppContext.getInstance().getReportService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));
        String actionParam = req.getParameter("action");

        Report report = reportService.find(reportID);
        reportService.update(actionParam, report, report.getSpeaker());

        return req.getContextPath() + "/pages/report/view?reportID=" + reportID;
    }
}
