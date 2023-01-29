package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.notification.ReportNotificationManager;

/**
 * Action to approve the report.
 * Moderator consolidates the speaker for the report.
 *
 * @author Stanislav Bozhevskyi
 */
public class ConsolidateReportByModerator extends ApprovalOfReportAction {
    private static final String NOTIFY_MSG = "Moderator consolidate you as a speaker for this report.";

    @Override
    public void execute(Report report, User speaker) {
        report.setStatus(Report.Status.CONFIRMED);
        report.setSpeaker(speaker);
    }

    @Override
    public void commit(Report report, User speaker) {
        super.commit(report, speaker);
        ReportNotificationManager.notifySpeaker(speaker, report, NOTIFY_MSG);
    }
}
