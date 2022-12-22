package com.nextvoyager.conferences.service.approvalofreport;

import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.AcceptOfferByModerator;
import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.AcceptSuggestionByModerator;
import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.DenyOfferModerator;
import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.DenySuggestionByModerator;
import com.nextvoyager.conferences.service.approvalofreport.speakerapproval.*;

import java.util.HashMap;
import java.util.Map;

public class ApprovalOfReportFactory {
    private static final Map<String, ApprovalOfReportAction> changeReportActionMap = new HashMap<>();

    static {
        changeReportActionMap.put("accept-propose-speaker", new AcceptProposeBySpeaker());
        changeReportActionMap.put("cancel-propose-speaker", new CancelProposeBySpeaker());
        changeReportActionMap.put("cancel-offer-speaker", new CancelOfferBySpeaker());
        changeReportActionMap.put("make-suggestion-speaker", new MakeSuggestionBySpeaker());
        changeReportActionMap.put("cancel-suggestion-speaker", new CancelSuggestionBySpeaker());

        changeReportActionMap.put("accept-offer-moderator", new AcceptOfferByModerator());
        changeReportActionMap.put("deny-offer-moderator", new DenyOfferModerator());
        changeReportActionMap.put("accept-suggestion-moderator", new AcceptSuggestionByModerator());
        changeReportActionMap.put("deny-suggestion-moderator", new DenySuggestionByModerator());
    }

    public static ApprovalOfReportAction getChangeReportAction(String action) {
        return changeReportActionMap.get(action);
    }
}
