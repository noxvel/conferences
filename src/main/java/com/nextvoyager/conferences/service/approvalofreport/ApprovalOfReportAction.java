package com.nextvoyager.conferences.service.approvalofreport;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class represents an abstraction for report approval action.
 *
 * @author Stanislav Bozhevskyi
 */
public abstract class ApprovalOfReportAction {

    private static final Logger LOGGER = LogManager.getLogger(ApprovalOfReportAction.class);

    public abstract void execute(Report report, User speaker);

    public void commit(Report report) {
        LOGGER.info("Approval of report action - {"
                + this.getClass().getName()
                + "} for report - "
                + report.toString());

    }
}
