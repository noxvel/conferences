package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

import java.util.List;

public interface ReportService {
    Report find(Integer reportID);

    void update(Report report);

    void create(Report report);

    List<Report> list(Integer eventID);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Report.Status status);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, User speaker);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, User speaker, Report.Status status);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, Report.Status status);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, User speaker);
    ReportDAO.ListWithCountResult listWithPagination(int page, int limit, Integer eventID, User speaker, Report.Status status);

    void update(String speakerAction, Report report, User speaker);

    void create(String approvalAction, Report report, User speaker);
}
