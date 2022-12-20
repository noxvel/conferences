package com.nextvoyager.conferences.controller.speaker;

import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
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

@WebServlet("/speaker-report-list")
public class SpeakerReportListController extends HttpServlet {

    ReportService reportService = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageParam = req.getParameter("page");

        // Default values for list of reports
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        HttpSession session = req.getSession(false);

//        if (session != null && session.getAttribute("user") != null) {
            User speaker = (User) session.getAttribute("user");
//        }

        ReportDAO.ListWithCountResult countAndList;

        HttpSession currentSession = req.getSession();
        Optional<Report.Status> reportStatusFilter = Optional.ofNullable((Report.Status) currentSession.getAttribute("reportStatusFilter"));
        if (reportStatusFilter.isEmpty()) {
            countAndList = reportService.listWithPagination(page, limit, speaker);
        } else {
            countAndList = reportService.listWithPagination(page, limit, speaker, reportStatusFilter.get());
        }

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("reports", countAndList.getList());
        req.setAttribute("reportStatusFilter", reportStatusFilter.orElse(null));
        req.setAttribute("reportStatuses", Report.Status.values());
        req.setAttribute("page", page);
        req.setAttribute("numOfPages", numOfPages);

        req.getRequestDispatcher("/WEB-INF/jsp/speaker-report-list.jsp").forward(req, resp);
    }
}
