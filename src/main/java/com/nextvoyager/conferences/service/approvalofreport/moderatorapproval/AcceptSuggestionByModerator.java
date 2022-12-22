package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

public class AcceptSuggestionByModerator implements ApprovalOfReportAction {
    @Override
    public void change(Report report, User speaker) {
        report.setStatus(Report.Status.CONFIRMED);
    }
}
