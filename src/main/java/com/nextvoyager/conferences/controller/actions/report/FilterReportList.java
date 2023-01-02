package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/report-list-filter")
public class FilterReportList implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String statusFilterParam = req.getParameter("reportStatusFilter");
        String redirectPath = req.getParameter("redirectPath");

        if (!statusFilterParam.equals("")) {
            req.getSession().setAttribute("reportStatusFilter", Report.Status.valueOf(statusFilterParam));
        } else {
            req.getSession().removeAttribute("reportStatusFilter");
        }

//        resp.sendRedirect(redirectPath);
        return redirectPath;
    }
}
