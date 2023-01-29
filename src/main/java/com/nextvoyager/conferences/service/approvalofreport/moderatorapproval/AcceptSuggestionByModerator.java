package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.notification.ReportNotificationManager;

/**
 * Action to approve the report.
 * Moderator accepts suggestion from the speaker to be a speaker of the report.
 *
 * @author Stanislav Bozhevskyi
 */
public class AcceptSuggestionByModerator extends ApprovalOfReportAction {
    private static final String NOTIFY_MSG = "Moderator accepted your suggestion to be a speaker of the report";

    @Override
    public void execute(Report report, User speaker) {
        report.setStatus(Report.Status.CONFIRMED);
    }

    @Override
    public void commit(Report report, User speaker) {
        super.commit(report, speaker);
        ReportNotificationManager.notifySpeaker(speaker, report, NOTIFY_MSG);
    }
}
