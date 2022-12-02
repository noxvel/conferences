package com.nextvoyager.conferences.controller.report;

import com.nextvoyager.conferences.dao.DAOFactory;
import com.nextvoyager.conferences.dao.report.ReportDAO;
import com.nextvoyager.conferences.dao.user.UserDAO;
import com.nextvoyager.conferences.model.Event;
import com.nextvoyager.conferences.model.Report;
import com.nextvoyager.conferences.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

@WebServlet("/event/report/create")
public class ReportCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventID = Integer.valueOf(req.getParameter("eventID"));

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain ReportDAO.
        UserDAO userDAO = javabase.getUserDAO();

        List<User> speakers = userDAO.listWithOneRole(User.Role.SPEAKER);

        req.setAttribute("statuses", Report.Status.values());
        req.setAttribute("speakers", speakers);
        req.setAttribute("eventID", eventID);
        req.getRequestDispatcher("report-create.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String topicParam = req.getParameter("topic");
        String speakerParam = req.getParameter("speaker");
        String eventParam = req.getParameter("event");
        String statusParam = req.getParameter("status");
        String descriptionParam = req.getParameter("description");

        // TODO: 01.12.2022 create validation to input data

        Report report = new Report();
        report.setTopic(topicParam);
        if (!speakerParam.equals("")) {
            report.setSpeaker(new User(Integer.valueOf(speakerParam)));
        } else {
            report.setSpeaker(new User());
        }
        report.setEvent(new Event(Integer.valueOf(eventParam)));
        report.setStatus(Report.Status.valueOf(statusParam));
        report.setDescription(descriptionParam);

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance();

        // Obtain ReportDAO.
        ReportDAO reportDAO = javabase.getReportDAO();

        reportDAO.create(report);

//        StringJoiner sb = new StringJoiner(" - ");
//        sb.add(topicParam).add(speakerParam).add(eventParam).add(statusParam).add(descriptionParam);
//
//        System.out.println(sb.toString());

        resp.sendRedirect("view?reportID=" + report.getId());
    }
}
