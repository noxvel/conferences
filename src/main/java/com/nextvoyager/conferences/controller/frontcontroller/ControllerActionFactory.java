package com.nextvoyager.conferences.controller.frontcontroller;
import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.actions.ChangeLanguageAction;
import com.nextvoyager.conferences.controller.actions.HomePageAction;
import com.nextvoyager.conferences.controller.actions.event.*;
import com.nextvoyager.conferences.controller.actions.report.*;
import com.nextvoyager.conferences.controller.actions.user.*;
import com.nextvoyager.conferences.controller.actions.user.moderator.ModeratorReportApprovalAction;
import com.nextvoyager.conferences.controller.actions.user.speaker.SpeakerReportApprovalAction;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.ReportService;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.approvalofreport.moderatorapproval.NoApprovalAction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.*;

/**
 * Factory method for getting the controller action that matches the HTTP method
 * and request path of the received request.
 *
 * @author Stanislav Bozhevskyi
 */
public class ControllerActionFactory {

    private static final UserService userService = AppContext.getInstance().getUserService();
    private static final EventService eventService = AppContext.getInstance().getEventService();
    private static final ReportService reportService = AppContext.getInstance().getReportService();

    private static final Map<String, ControllerAction> actions = new HashMap<>();

    private static final String POST = "POST";
    private static final String GET = "GET";

    static{
        actions.put(GET + HOME, new HomePageAction(eventService));
        actions.put(POST + CHANGE_LANGUAGE, new ChangeLanguageAction());

        actions.put(GET + EVENT_CREATE, new EventCreateGetAction());
        actions.put(POST + EVENT_CREATE, new EventCreatePostAction(eventService));
        actions.put(GET + EVENT_DELETE, new EventDeleteAction(eventService));
        actions.put(GET + EVENT_EDIT, new EventEditGetAction(eventService));
        actions.put(POST + EVENT_EDIT, new EventEditPostAction(eventService));
        actions.put(POST + EVENT_REGISTER, new EventRegisterUserAction(eventService));
        actions.put(GET + EVENT_STATISTICS, new EventStatisticsAction(eventService));
        actions.put(GET + EVENT_SAVE_STATISTICS, new EventStatisticsSaveAction(eventService));
        actions.put(GET + EVENT_VIEW, new EventViewAction(eventService,reportService,userService));
        actions.put(POST + EVENT_LIST_FILTER, new EventListFilterAction());
        actions.put(POST + EVENT_LIST_SORT, new EventListSortAction());

        actions.put(GET + REPORT_CREATE, new ReportCreateGetAction(userService));
        actions.put(POST + REPORT_CREATE, new ReportCreatePostAction(reportService));
        actions.put(GET + REPORT_DELETE, new ReportDeleteAction(reportService));
        actions.put(GET + REPORT_EDIT, new ReportEditGetAction(reportService,userService));
        actions.put(POST + REPORT_EDIT, new ReportEditPostAction(reportService));
        actions.put(GET + REPORT_LIST, new ReportListAction(reportService,userService));
        actions.put(GET + REPORT_VIEW, new ReportViewAction(reportService));
        actions.put(POST + REPORT_LIST_FILTER, new ReportListFilterAction());

        actions.put(GET + USER_LOGIN, new LoginUserGetAction());
        actions.put(POST + USER_LOGIN, new LoginUserPostAction(userService));
        actions.put(GET + USER_PROFILE, new ProfileUserGetAction());
        actions.put(POST + USER_PROFILE, new ProfileUserPostAction(userService));
        actions.put(GET + USER_REGISTRATION, new RegistrationUserGetAction());
        actions.put(POST + USER_REGISTRATION, new RegistrationUserPostAction(userService));
        actions.put(GET + USER_CHANGE_PASSWORD, new ChangeUserPasswordGetAction());
        actions.put(POST + USER_CHANGE_PASSWORD, new ChangeUserPasswordPostAction(userService));
        actions.put(GET + USER_SIGN_OUT, new SignOutUserAction());
        actions.put(GET + MODERATOR_REPORT_APPROVAL, new ModeratorReportApprovalAction(reportService));
        actions.put(GET + SPEAKER_REPORT_APPROVAL, new SpeakerReportApprovalAction(reportService));
        actions.put(GET + USER_LIST, new UserListAction(userService));

    }

    public static ControllerAction getAction(HttpServletRequest request) {
        return actions.get(request.getMethod() + request.getPathInfo());
    }
}
