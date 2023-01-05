package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportFactory;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO;
    private final UserDAO userDAO;

    public static ReportServiceImpl getInstance(ReportDAO reportDAO,UserDAO userDAO) {
        return new ReportServiceImpl(reportDAO, userDAO);
    }

    private ReportServiceImpl(ReportDAO reportDAO, UserDAO userDAO) {
        this.reportDAO = reportDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Report find(Integer reportID) {
        Report report = reportDAO.find(reportID);
        if (report != null && report.getSpeaker() != null) {
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
    public List<Report> list(Integer eventID) {
        return reportDAO.list(eventID);
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
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, User speaker) {
        return reportDAO.listWithPagination(page, limit, eventID, speaker);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, User speaker, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, eventID, speaker, status);
    }

    @Override
    public ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, eventID, status);
    }

    @Override
    public void delete(Report report) {
        reportDAO.delete(report);
    }

    @Override
    public void create(String approvalAction, Report report, User speaker) {

        ApprovalOfReportAction approvalOfReportAction = ApprovalOfReportFactory.getChangeReportAction(approvalAction);

        approvalOfReportAction.execute(report, speaker);

        reportDAO.create(report);

        approvalOfReportAction.commit(report);

    }

    @Override
    public void update(String approvalAction, Report report, User speaker) {

        ApprovalOfReportAction approvalOfReportAction = ApprovalOfReportFactory.getChangeReportAction(approvalAction);

        approvalOfReportAction.execute(report, speaker);

        reportDAO.update(report);

        approvalOfReportAction.commit(report);

    }


}
