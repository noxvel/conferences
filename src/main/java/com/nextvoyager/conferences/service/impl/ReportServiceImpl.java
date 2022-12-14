package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    DAOFactory javabase = DAOFactory.getInstance();
    ReportDAO reportDAO = javabase.getReportDAO();
    UserDAO userDAO = javabase.getUserDAO();

    public static ReportServiceImpl getInstance() {
        return new ReportServiceImpl();
    }

    @Override
    public Report find(Integer reportID) {
        Report report = reportDAO.find(reportID);
        if (report != null && report.getSpeaker().getId() != 0) {
            User speaker = userDAO.find(report.getSpeaker().getId());
            report.setSpeaker(speaker);
        }
        return report;
    }

    @Override
    public void update(Report report) {
        reportDAO.update(report);
    }

    @Override
    public void create(Report report) {
        reportDAO.create(report);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(Integer eventID, int page, int limit) {
        return reportDAO.listWithPagination(eventID, page, limit);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(Integer eventID, int page, int limit, Report.Status status) {
        return reportDAO.listWithPagination(eventID, page, limit, status);
    }
}
