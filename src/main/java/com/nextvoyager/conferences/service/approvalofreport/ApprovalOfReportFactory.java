package com.nextvoyager.conferences.service.approvalofreport;

import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.*;
import com.nextvoyager.conferences.service.approvalofreport.speakerapproval.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a factory to get the appropriate action to approve the report
 *
 * @author Stanislav Bozhevskyi
 */
public class ApprovalOfReportFactory {
    private static final Map<String, ApprovalOfReportAction> changeReportActionMap = new HashMap<>();

    static {
        changeReportActionMap.put("accept-propose-speaker", new AcceptProposeBySpeaker());
        changeReportActionMap.put("cancel-propose-speaker", new CancelProposeBySpeaker());
        changeReportActionMap.put("cancel-offer-speaker", new CancelOfferBySpeaker());
        changeReportActionMap.put("make-suggestion-speaker", new MakeSuggestionBySpeaker());
        changeReportActionMap.put("cancel-suggestion-speaker", new CancelSuggestionBySpeaker());
        changeReportActionMap.put("offer-report-speaker", new OfferReportBySpeaker());

        changeReportActionMap.put("consolidate-report-moderator", new ConsolidateReportByModerator());
        changeReportActionMap.put("propose-to-speaker-moderator", new ProposeToSpeakerByModerator());
        changeReportActionMap.put("set-free-report-moderator", new SetFreeReportByModerator());
        changeReportActionMap.put("accept-offer-moderator", new AcceptOfferByModerator());
        changeReportActionMap.put("deny-offer-moderator", new DenyOfferModerator());
        changeReportActionMap.put("accept-suggestion-moderator", new AcceptSuggestionByModerator());
        changeReportActionMap.put("deny-suggestion-moderator", new DenySuggestionByModerator());
        changeReportActionMap.put("cancel-report-moderator", new CancelReportByModerator());

        changeReportActionMap.put("no-approval-action", new NoApprovalAction());
    }

    public static ApprovalOfReportAction getChangeReportAction(String actionParam) {
        ApprovalOfReportAction action = changeReportActionMap.get(actionParam);
        return Objects.requireNonNullElseGet(action, NoApprovalAction::new);
    }
}
