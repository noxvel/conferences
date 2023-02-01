package com.nextvoyager.conferences.service.approvalofreport;

import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.*;
import com.nextvoyager.conferences.service.approvalofreport.speakerapproval.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.nextvoyager.conferences.service.approvalofreport.ApprovalOfReportAction.*;

/**
 * This class represents a factory to get the appropriate action to approve the report
 *
 * @author Stanislav Bozhevskyi
 */
public class ApprovalOfReportFactory {
    private static final Map<String, ApprovalOfReportAction> changeReportActionMap = new HashMap<>();

    static {
        changeReportActionMap.put(ACCEPT_PROPOSE_SPEAKER, new AcceptProposeBySpeaker());
        changeReportActionMap.put(CANCEL_PROPOSE_SPEAKER, new CancelProposeBySpeaker());
        changeReportActionMap.put(CANCEL_OFFER_SPEAKER, new CancelOfferBySpeaker());
        changeReportActionMap.put(MAKE_SUGGESTION_SPEAKER, new MakeSuggestionBySpeaker());
        changeReportActionMap.put(CANCEL_SUGGESTION_SPEAKER, new CancelSuggestionBySpeaker());
        changeReportActionMap.put(OFFER_REPORT_SPEAKER, new OfferReportBySpeaker());

        changeReportActionMap.put(CONSOLIDATE_REPORT_MODERATOR, new ConsolidateReportByModerator());
        changeReportActionMap.put(PROPOSE_TO_SPEAKER_MODERATOR, new ProposeToSpeakerByModerator());
        changeReportActionMap.put(SET_FREE_REPORT_MODERATOR, new SetFreeReportByModerator());
        changeReportActionMap.put(ACCEPT_OFFER_MODERATOR, new AcceptOfferByModerator());
        changeReportActionMap.put(DENY_OFFER_MODERATOR, new DenyOfferModerator());
        changeReportActionMap.put(ACCEPT_SUGGESTION_MODERATOR, new AcceptSuggestionByModerator());
        changeReportActionMap.put(DENY_SUGGESTION_MODERATOR, new DenySuggestionByModerator());
        changeReportActionMap.put(CANCEL_REPORT_MODERATOR, new CancelReportByModerator());

        changeReportActionMap.put(NO_APPROVAL_ACTION, new NoApprovalAction());
    }

    public static ApprovalOfReportAction getChangeReportAction(String actionParam) {
        ApprovalOfReportAction action = changeReportActionMap.get(actionParam);
        return Objects.requireNonNullElseGet(action, NoApprovalAction::new);
    }
}
