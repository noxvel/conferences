package com.nextvoyager.conferences.service.approvalofreport.moderatorapproval;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction;

/**
 * Action to approve the report.
 * No approval of the report action.
 *
 * @author Stanislav Bozhevskyi
 */
public class NoApprovalAction extends ApprovalOfReportAction {
    @Override
    public void execute(Report report, User speaker) {

    }
}
