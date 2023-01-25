package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

/**
 * Action to approve the report.
 * The moderator propose the report to the selected speaker.
 *
 * @author Stanislav Bozhevskyi
 */
public class ProposeToSpeakerByModerator extends ApprovalOfReportAction {
    @Override
    public void execute(Report report, User speaker) {
        report.setSpeaker(speaker);
        report.setStatus(Report.Status.PROPOSE_TO_SPEAKER);
    }
}
