package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//("/report/delete")
public class ReportDeleteAction implements ControllerAction {

    private final ReportService reportService;

    public ReportDeleteAction(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));

        Report report = reportService.find(reportID);
        reportService.delete(report);

        return PREFIX_PATH + "/event/view?eventID=" + report.getEvent().getId();
    }

}
