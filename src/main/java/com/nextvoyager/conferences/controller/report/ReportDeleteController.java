package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/report/delete")
public class ReportDeleteController extends HttpServlet {

    private final ReportService reportService = AppContext.getInstance().getReportService();
    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));

        Report report = reportService.find(reportID);
        reportService.delete(report);

        resp.sendRedirect(req.getContextPath() + "/event/view?eventID=" + report.getEvent().getId());
    }

}
