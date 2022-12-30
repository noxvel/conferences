package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

public class CancelReportByModerator extends ApprovalOfReportAction {
    @Override
    public void execute(Report report, User speaker) {
        report.setStatus(Report.Status.CANCELED);
    }
}
