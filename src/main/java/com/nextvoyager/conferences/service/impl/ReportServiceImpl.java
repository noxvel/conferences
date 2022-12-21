package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.changereport.ChangeReportAction;
import com.nextvoyager.conferences.service.changereport.ChangeReportFactory;

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
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit) {
        return reportDAO.listWithPagination(page, limit);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, status);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, User speaker) {
        return reportDAO.listWithPagination(page, limit, speaker);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, User speaker, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, speaker, status);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID) {
        return reportDAO.listWithPagination(page, limit, eventID);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, eventID, status);
    }

    @Override
    public void changeStatusBySpeaker(String action, Integer reportID, User speaker) {
        Report report = find(reportID);

        ChangeReportAction changeReportAction = ChangeReportFactory.getChangeReportAction(action);

        if (changeReportAction != null) {
            changeReportAction.change(report, speaker);
            reportDAO.update(report);
        }
    }

    @Override
    public void changeStatusByModerator(String action, Integer reportID) {
        Report report = find(reportID);

        ChangeReportAction changeReportAction = ChangeReportFactory.getChangeReportAction(action);

        if (changeReportAction != null) {
            changeReportAction.change(report, report.getSpeaker());
            reportDAO.update(report);
        }

    }

}
