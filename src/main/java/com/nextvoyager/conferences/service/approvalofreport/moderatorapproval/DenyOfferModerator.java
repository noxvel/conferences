package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.notification.ReportNotificationManager;

/**
 * Action to approve the report.
 * Moderator deny the report offered by the speaker.
 *
 * @author Stanislav Bozhevskyi
 */
public class DenyOfferModerator extends ApprovalOfReportAction {
    private static final String NOTIFY_MSG = "Moderator deny the report, that you offered";

    @Override
    public void execute(Report report, User speaker) {
        report.setStatus(Report.Status.CANCELED);
    }

    @Override
    public void commit(Report report, User speaker) {
        super.commit(report, speaker);
        ReportNotificationManager.notifySpeaker(speaker, report, NOTIFY_MSG);
    }
}
