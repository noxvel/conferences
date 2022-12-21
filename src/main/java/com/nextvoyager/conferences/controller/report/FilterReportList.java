package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.model.entity.Report;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/report-list-filter")
public class FilterReportList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectPath = req.getParameter("redirectPath").trim();
        String statusFilterParam = req.getParameter("reportStatusFilter");

        if (!statusFilterParam.equals("")) {
            req.getSession().setAttribute("reportStatusFilter", Report.Status.valueOf(statusFilterParam));
        } else {
            req.getSession().removeAttribute("reportStatusFilter");
        }

        resp.sendRedirect(redirectPath);
    }
}
