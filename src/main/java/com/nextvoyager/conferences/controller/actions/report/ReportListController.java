package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

//@WebServlet("/report-list")
public class ReportListController implements ControllerAction {

    private final ReportService reportService = AppContext.getInstance().getReportService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageParam = req.getParameter("page");

        // Default values for list of reports
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        ReportDAO.ListWithCountResult countAndList;

        HttpSession currentSession = req.getSession();
        User.Role userRole = (User.Role) currentSession.getAttribute("userRole");
        User currentUser = (User) currentSession.getAttribute("user");

        Optional<Report.Status> reportStatusFilter = Optional.ofNullable((Report.Status) currentSession.getAttribute("reportStatusFilter"));
        if (userRole == User.Role.SPEAKER) {
            if (reportStatusFilter.isEmpty()) {
                countAndList = reportService.listWithPagination(page, limit, currentUser);
            } else {
                countAndList = reportService.listWithPagination(page, limit, currentUser, reportStatusFilter.get());
            }
        } else {
            if (reportStatusFilter.isEmpty()) {
                countAndList = reportService.listWithPagination(page, limit);
            } else {
                countAndList = reportService.listWithPagination(page, limit, reportStatusFilter.get());
            }
        }

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("reports", countAndList.getList());
        req.setAttribute("reportStatusFilter", reportStatusFilter.orElse(null));
        req.setAttribute("reportStatuses", Report.Status.values());
        req.setAttribute("page", page);
        req.setAttribute("numOfPages", numOfPages);

//        req.getRequestDispatcher("/WEB-INF/jsp/report/list.jsp").forward(req, resp);
        return "report/list";
    }
}
