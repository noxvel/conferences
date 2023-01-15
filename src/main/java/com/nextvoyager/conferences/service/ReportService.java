package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

import java.util.List;

public interface ReportService {
    Report find(Integer reportID);
    void create(Report report);
    void update(Report report);
    void update(String speakerAction, Report report, User speaker);
    void create(String approvalAction, Report report, User speaker);
    void delete(Report report);
    List<Report> list(Integer eventID);
    ListWithCount<Report> listWithPagination(int page, int limit);
    ListWithCount<Report> listWithPagination(int page, int limit, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, User speaker);
    ListWithCount<Report> listWithPagination(int page, int limit, User speaker, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID);
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID, User speaker);
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID, User speaker, Report.Status status);
}
