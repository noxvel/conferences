package com.nextvoyager.conferences.controller.actions.report;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

//@WebServlet("/report/create")
public class ReportCreateControllerGet implements ControllerAction {

    private final ReportService reportService = AppContext.getInstance().getReportService();
    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        Report.Status[] statuses;
        List<User> speakers;

        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser.getRole() == User.Role.SPEAKER) {
            speakers = List.of(currentUser);
            statuses = new Report.Status[]{Report.Status.OFFERED_BY_SPEAKER};
        } else {
            speakers = userService.listWithOneRole(User.Role.SPEAKER);
            statuses = Report.Status.values();
        }

        req.setAttribute("statuses", statuses);
        req.setAttribute("speakers", speakers);
        req.setAttribute("eventID", eventID);
//        req.getRequestDispatcher("/WEB-INF/jsp/report/create.jsp").forward(req,resp);
        return "report/create";

    }

}
