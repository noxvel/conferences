package com.nextvoyager.conferences.service.changereport;

import com.nextvoyager.conferences.service.changereport.moderator.AcceptOfferByModerator;
import com.nextvoyager.conferences.service.changereport.moderator.AcceptSuggestionByModerator;
import com.nextvoyager.conferences.service.changereport.moderator.DenyOfferModerator;
import com.nextvoyager.conferences.service.changereport.moderator.DenySuggestionByModerator;
import com.nextvoyager.conferences.service.changereport.speaker.*;

import java.util.HashMap;
import java.util.Map;

public class ChangeReportFactory {
    private static final Map<String,ChangeReportAction> changeReportActionMap = new HashMap<>();

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

    public static ChangeReportAction getChangeReportAction(String action) {
        return changeReportActionMap.get(action);
    }
}
