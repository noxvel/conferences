package com.nextvoyager.conferences.service.approvalofreport.speakerapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

/**
 * Action to approve the report.
 * The speaker offers his report to the event.
 *
 * @author Stanislav Bozhevskyi
 */
public class OfferReportBySpeaker extends ApprovalOfReportAction {
    @Override
    public void execute(Report report, User speaker) {
        report.setSpeaker(speaker);
        report.setStatus(Report.Status.OFFERED_BY_SPEAKER);
    }
}
