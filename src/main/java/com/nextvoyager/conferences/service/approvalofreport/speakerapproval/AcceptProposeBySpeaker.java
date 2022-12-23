package com.nextvoyager.conferences.service.approvalofreport.speakerapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

public class AcceptProposeBySpeaker implements ApprovalOfReportAction {
    @Override
    public void change(Report report, User speaker) {
        report.setStatus(Report.Status.CONFIRMED);
    }
}