package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

public class DenySuggestionByModerator implements ApprovalOfReportAction {
    @Override
    public void change(Report report, User speaker) {
        report.setStatus(Report.Status.FREE);
        report.setSpeaker(null);
    }
}
