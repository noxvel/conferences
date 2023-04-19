package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.notification.ReportNotificationManager;

/**
 * Action to approve the report.
 * Moderator cancels the report.
 *
 * @author Stanislav Bozhevskyi
 */
public class CancelReportByModerator extends ApprovalOfReportAction {
    private static final String NOTIFY_MSG = "Moderator canceled the report.";

    @Override
    public void execute(Report report, User speaker) {
        report.setStatus(Report.Status.CANCELED);
    }

    @Override
    public void commit(Report report, User speaker) {
        super.commit(report, speaker);
        if (speaker != null)
            ReportNotificationManager.notifySpeaker(speaker, report, NOTIFY_MSG);
    }
}
