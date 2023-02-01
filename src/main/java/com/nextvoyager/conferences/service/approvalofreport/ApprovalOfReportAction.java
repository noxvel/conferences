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

    public static final String ACCEPT_PROPOSE_SPEAKER = "accept-propose-speaker";
    public static final String CANCEL_PROPOSE_SPEAKER = "cancel-propose-speaker";
    public static final String CANCEL_OFFER_SPEAKER = "cancel-offer-speaker";
    public static final String MAKE_SUGGESTION_SPEAKER = "make-suggestion-speaker";
    public static final String CANCEL_SUGGESTION_SPEAKER = "cancel-suggestion-speaker";
    public static final String OFFER_REPORT_SPEAKER = "offer-report-speaker";

    public static final String CONSOLIDATE_REPORT_MODERATOR = "consolidate-report-moderator";
    public static final String PROPOSE_TO_SPEAKER_MODERATOR = "propose-to-speaker-moderator";
    public static final String SET_FREE_REPORT_MODERATOR = "set-free-report-moderator";
    public static final String ACCEPT_OFFER_MODERATOR = "accept-offer-moderator";
    public static final String DENY_OFFER_MODERATOR = "deny-offer-moderator";
    public static final String ACCEPT_SUGGESTION_MODERATOR = "accept-suggestion-moderator";
    public static final String DENY_SUGGESTION_MODERATOR = "deny-suggestion-moderator";
    public static final String CANCEL_REPORT_MODERATOR = "cancel-report-moderator";

    public static final String NO_APPROVAL_ACTION = "no-approval-action";

    private static final Logger LOG = LogManager.getLogger(ApprovalOfReportAction.class);

    public abstract void execute(Report report, User speaker);

    public void commit(Report report, User speaker) {
        LOG.info("Approval of report action - {"
                + this.getClass().getName()
                + "} for report - "
                + report.toString());

    }
}
