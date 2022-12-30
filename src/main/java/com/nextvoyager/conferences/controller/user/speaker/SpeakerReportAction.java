package com.nextvoyager.conferences.controller.user.speaker;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/speaker-report-action")
public class SpeakerReportAction extends HttpServlet {

    private final ReportService reportService = AppContext.getInstance().getReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));
        String actionParam = req.getParameter("action");

        User speaker = (User) req.getSession().getAttribute("user");

        Report report = reportService.find(reportID);
        reportService.update(actionParam, report, speaker);

        resp.sendRedirect("report/view?reportID=" + reportID);
    }
}
