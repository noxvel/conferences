package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.impl.ReportServiceImpl;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/event/report/edit")
public class ReportEditController extends HttpServlet {

    ReportService reportService = ReportServiceImpl.getInstance();
    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer reportID = Integer.valueOf(req.getParameter("reportID"));

        List<User> speakers = userService.listWithOneRole(User.Role.SPEAKER);

        Report report = reportService.find(reportID);

        req.setAttribute("report", report);
        req.setAttribute("statuses", Report.Status.values());
        req.setAttribute("speakers", speakers);
        req.getRequestDispatcher("/WEB-INF/jsp/event/report/report-edit.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("reportID");
        String topicParam = req.getParameter("topic");
        String speakerParam = req.getParameter("speaker");
        String eventParam = req.getParameter("event");
        String statusParam = req.getParameter("status");
        String descriptionParam = req.getParameter("description");

        // TODO: 01.12.2022 create validation to input data

        Report report = new Report();
        report.setId(Integer.valueOf(idParam));
        report.setTopic(topicParam);
        if (!speakerParam.equals("")) {
            report.setSpeaker(new User(Integer.valueOf(speakerParam)));
        } else {
            report.setSpeaker(new User());
        }
        report.setEvent(new Event(Integer.valueOf(eventParam)));
        report.setStatus(Report.Status.valueOf(statusParam));
        report.setDescription(descriptionParam);

        reportService.update(report);

        resp.sendRedirect("view?reportID=" + report.getId());
    }
}
