package com.nextvoyager.conferences.controller.actions.user.moderator;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.event.ContainerListener;
import java.io.IOException;

//@WebServlet("/moderator-report-action")
public class ModeratorReportAction implements ControllerAction {

    private final ReportService reportService = AppContext.getInstance().getReportService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));
        String actionParam = req.getParameter("action");

        Report report = reportService.find(reportID);
        reportService.update(actionParam, report, report.getSpeaker());

//        resp.sendRedirect("report/view?reportID=" + reportID);
        return req.getContextPath() + "/pages/report/view?reportID=" + reportID;
    }
}
