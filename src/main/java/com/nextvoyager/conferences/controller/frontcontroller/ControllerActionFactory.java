package com.nextvoyager.conferences.controller.frontcontroller;
import com.nextvoyager.conferences.controller.actions.ChangeLanguageAction;
import com.nextvoyager.conferences.controller.actions.HomePageAction;
import com.nextvoyager.conferences.controller.actions.event.*;
import com.nextvoyager.conferences.controller.actions.report.*;
import com.nextvoyager.conferences.controller.actions.user.*;
import com.nextvoyager.conferences.controller.actions.user.moderator.ModeratorReportApprovalAction;
import com.nextvoyager.conferences.controller.actions.user.speaker.SpeakerReportApprovalAction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.*;

public class ControllerActionFactory {

    private static final Map<String, ControllerAction> actions = new HashMap<>();

    public static final String POST = "POST/";
    public static final String GET = "GET/";

    static{
        actions.put(GET + HOME, new HomePageAction());
        actions.put(POST + CHANGE_LANGUAGE, new ChangeLanguageAction());

        actions.put(GET + EVENT_CREATE, new EventCreateGetAction());
        actions.put(POST + EVENT_CREATE, new EventCreatePostAction());
        actions.put(GET + EVENT_DELETE, new EventDeleteAction());
        actions.put(GET + EVENT_EDIT, new EventEditGetAction());
        actions.put(POST + EVENT_EDIT, new EventEditPostAction());
        actions.put(POST + EVENT_REGISTER, new EventRegisterAction());
        actions.put(GET + EVENT_STATISTICS, new EventStatisticsAction());
        actions.put(GET + EVENT_SAVE_STATISTICS, new EventStatisticsSaveAction());
        actions.put(GET + EVENT_VIEW, new EventViewAction());
        actions.put(POST + EVENT_LIST_FILTER, new EventListFilterAction());
        actions.put(POST + EVENT_LIST_SORT, new EventListSortAction());

        actions.put(GET + REPORT_CREATE, new ReportCreateGetAction());
        actions.put(POST + REPORT_CREATE, new ReportCreatePostAction());
        actions.put(GET + REPORT_DELETE, new ReportDeleteAction());
        actions.put(GET + REPORT_EDIT, new ReportEditGetAction());
        actions.put(POST + REPORT_EDIT, new ReportEditPostAction());
        actions.put(GET + REPORT_LIST, new ReportListAction());
        actions.put(GET + REPORT_VIEW, new ReportViewAction());
        actions.put(POST + REPORT_LIST_FILTER, new ReportListFilterAction());

        actions.put(GET + USER_LOGIN, new LoginUserGetAction());
        actions.put(POST + USER_LOGIN, new LoginUserPostAction());
        actions.put(GET + USER_PROFILE, new ProfileUserGetAction());
        actions.put(POST + USER_PROFILE, new ProfileUserPostAction());
        actions.put(GET + USER_REGISTRATION, new RegistrationUserGetAction());
        actions.put(POST + USER_REGISTRATION, new RegistrationUserPostAction());
        actions.put(GET + USER_CHANGE_PASSWORD, new ChangeUserPasswordGetAction());
        actions.put(POST + USER_CHANGE_PASSWORD, new ChangeUserPasswordPostAction());
        actions.put(GET + USER_SIGN_OUT, new SignOutUserAction());
        actions.put(GET + MODERATOR_REPORT_APPROVAL, new ModeratorReportApprovalAction());
        actions.put(GET + SPEAKER_REPORT_APPROVAL, new SpeakerReportApprovalAction());

    }

    public static ControllerAction getAction(HttpServletRequest request) {
        return actions.get(request.getMethod() + request.getPathInfo());
    }
}
