package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Report;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//("/report/list-filter")
public class ReportListFilterAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String statusFilterParam = req.getParameter("reportStatusFilter");
        String redirectPath = req.getParameter("redirectPath");

        if (!statusFilterParam.equals("")) {
            req.getSession().setAttribute("reportStatusFilter", Report.Status.valueOf(statusFilterParam));
        } else {
            req.getSession().removeAttribute("reportStatusFilter");
        }

        return req.getContextPath() + "/pages/" + redirectPath;
//        return redirectPath;
    }
}
