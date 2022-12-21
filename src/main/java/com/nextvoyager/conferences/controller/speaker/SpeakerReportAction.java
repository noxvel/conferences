package com.nextvoyager.conferences.controller.speaker;

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

    ReportService reportService = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));
        String actionParam = req.getParameter("action");

        reportService.changeStatusBySpeaker(actionParam, reportID);

        resp.sendRedirect("speaker-report-list");

    }
}
