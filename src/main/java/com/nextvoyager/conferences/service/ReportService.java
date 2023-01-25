package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

/**
 * Report service interface
 *
 * @author Stanislav Bozhevskyi
 */
public interface ReportService {
    Report find(Integer reportID);
    void create(Report report);
    void create(String approvalAction, Report report, User speaker);
    void update(Report report);
    void update(String speakerAction, Report report, User speaker);
    void delete(Report report);
    List<Report> list(Event event);
    ListWithCount<Report> listWithPagination(int page, int limit);
    ListWithCount<Report> listWithPagination(int page, int limit, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, User speaker);
    ListWithCount<Report> listWithPagination(int page, int limit, User speaker, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, Event event);
    ListWithCount<Report> listWithPagination(int page, int limit, Event event, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, Event event, User speaker);
    ListWithCount<Report> listWithPagination(int page, int limit, Event event, User speaker, Report.Status status);
}
