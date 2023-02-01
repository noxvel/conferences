package com.nextvoyager.conferences.service.approvalofreport;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

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
