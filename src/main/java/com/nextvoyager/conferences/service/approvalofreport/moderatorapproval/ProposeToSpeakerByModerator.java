package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;
import com.nextvoyager.conferences.service.notification.ReportNotificationManager;

/**
 * Action to approve the report.
 * Moderator propose the report to the selected speaker.
 *
 * @author Stanislav Bozhevskyi
 */
public class ProposeToSpeakerByModerator extends ApprovalOfReportAction {
    private static final String NOTIFY_MSG = "The moderator invited you to act as a speaker for this report.";

    @Override
    public void execute(Report report, User speaker) {
        report.setSpeaker(speaker);
        report.setStatus(Report.Status.PROPOSE_TO_SPEAKER);
    }

    @Override
    public void commit(Report report, User speaker) {
        super.commit(report, speaker);
        ReportNotificationManager.notifySpeaker(speaker, report, NOTIFY_MSG);
    }
}
