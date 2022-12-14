package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.report.ReportDAO;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

public interface ReportService {
    Report find(Integer reportID);

    void update(Report report);

    void create(Report report);
    ReportDAO.ListWithCountResult listWithPagination(Integer eventID, int page, int limit);

    ReportDAO.ListWithCountResult listWithPagination(Integer eventID, int page, int limit, Report.Status status);

}
