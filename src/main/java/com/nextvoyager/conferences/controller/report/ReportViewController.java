package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.Report;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/event/report/view")
public class ReportViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain UserDAO.
        ReportDAO reportDAO = javabase.getReportDAO();

        Report report = reportDAO.find(reportID);

        req.setAttribute("report", report);
        req.getRequestDispatcher("report-view.jsp").forward(req,resp);
    }
}
