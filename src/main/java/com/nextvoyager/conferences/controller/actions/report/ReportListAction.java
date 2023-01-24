package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.PaginationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

/**
 * Get list of reports
 * Path "/report-list".
 *
 * @author Stanislav Bozhevskyi
 */
public class ReportListAction implements ControllerAction {

    private final ReportService reportService;
    private final UserService userService;

    public ReportListAction(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int page = PaginationUtil.handlePaginationPageParameter(req);
        int limit = PaginationUtil.handlePaginationLimitParameter(req);

        ListWithCount<Report> countAndList;

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

        req.getSession().removeAttribute("reportStatusFilter");

        int numOfPages = PaginationUtil.getNumOfPages(countAndList.getCount(),limit);

        List<Report> listOfReports = countAndList.getList();
        listOfReports.stream()
                .filter(report -> report.getSpeaker() != null)
                .forEach(report -> {
                    report.setSpeaker(userService.find(report.getSpeaker().getId()));
                });

        req.setAttribute("page", page);
        req.setAttribute("limit", limit);
        req.setAttribute("reports", listOfReports);
        req.setAttribute("reportStatusFilter", reportStatusFilter.orElse(null));
        req.setAttribute("reportStatuses", Report.Status.values());
        req.setAttribute("numOfPages", numOfPages);

        return REPORT_LIST;
    }
}
