package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportFactory;

import java.util.List;

/**
 * User entity class
 * The class which implements {@link ReportService}Event service interface
 *
 * @author Stanislav Bozhevskyi
 */
public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO;
    private final UserDAO userDAO;

    public ReportServiceImpl(ReportDAO reportDAO, UserDAO userDAO) {
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
    public List<Report> list(Event event) {
        return reportDAO.list(event);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit) {
        return reportDAO.listWithPagination(page, limit);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, status);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, User speaker) {
        return reportDAO.listWithPagination(page, limit, speaker);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, User speaker, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, speaker, status);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, Event event) {
        return reportDAO.listWithPagination(page, limit, event);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, Event event, User speaker) {
        return reportDAO.listWithPagination(page, limit, event, speaker);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, Event event, User speaker, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, event, speaker, status);
    }

    @Override
    public ListWithCount<Report> listWithPagination(int page, int limit, Event event, Report.Status status) {
        return reportDAO.listWithPagination(page, limit, event, status);
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
