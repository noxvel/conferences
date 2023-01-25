package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

/**
 * Action to approve the report.
 * The moderator consolidates the speaker for the report.
 *
 * @author Stanislav Bozhevskyi
 */
public class ConsolidateReportByModerator extends ApprovalOfReportAction {
    @Override
    public void execute(Report report, User speaker) {
        report.setStatus(Report.Status.CONFIRMED);
        report.setSpeaker(speaker);
    }
}
