package com.nextvoyager.conferences.service.approvalofreport;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

public interface ApprovalOfReportAction {
    void change(Report report, User speaker);
}
