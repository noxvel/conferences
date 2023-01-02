package com.nextvoyager.conferences.controller.frontcontroller;

import com.nextvoyager.conferences.controller.actions.ChangeLanguage;
import com.nextvoyager.conferences.controller.actions.HomePageController;
import com.nextvoyager.conferences.controller.actions.event.*;
import com.nextvoyager.conferences.controller.actions.report.*;
import com.nextvoyager.conferences.controller.actions.user.*;
import com.nextvoyager.conferences.controller.actions.user.moderator.ModeratorReportAction;
import com.nextvoyager.conferences.controller.actions.user.speaker.SpeakerReportAction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class ControllerActionFactory {

    private static final Map<String, ControllerAction> actions = new HashMap<>();

    static{
        actions.put("GET/home", new HomePageController());
        actions.put("POST/change-language", new ChangeLanguage());

        actions.put("GET/event/create", new EventCreateControllerPost());
        actions.put("POST/event/create", new EventDeleteController());
        actions.put("GET/event/edit", new EventEditControllerGet());
        actions.put("POST/event/edit", new EventEditControllerPost());
        actions.put("POST/event/register", new EventRegisterController());
        actions.put("GET/event/statistics", new EventStatisticsController());
        actions.put("GET/event/save-statistics", new EventStatisticsSave());
        actions.put("GET/event/view", new EventViewController());
        actions.put("POST/event-list-filter", new FilterEventList());
        actions.put("POST/event-list-sort", new SortEventList());

        actions.put("GET/report/create", new ReportCreateControllerGet());
        actions.put("POST/report/create", new ReportCreateControllerPost());
        actions.put("GET/report/delete", new ReportDeleteController());
        actions.put("GET/report/edit", new ReportEditControllerGet());
        actions.put("POST/report/edit", new ReportEditControllerPost());
        actions.put("GET/report/list", new ReportListController());
        actions.put("GET/report/view", new ReportViewController());

        actions.put("GET/moderator-report-action", new ModeratorReportAction());
        actions.put("GET/speaker-report-action", new SpeakerReportAction());
        actions.put("GET/user/change-password", new ChangeUserPasswordControllerGet());
        actions.put("POST/user/change-password", new ChangeUserPasswordControllerPost());
        actions.put("GET/user/login", new LoginUserControllerGet());
        actions.put("POST/user/login", new LoginUserControllerPost());
        actions.put("GET/user/profile", new ProfileUserControllerGet());
        actions.put("POST/user/profile", new ProfileUserControllerPost());
        actions.put("GET/user/registration", new RegistrationUserControllerGet());
        actions.put("POST/user/registration", new RegistrationUserControllerPost());
        actions.put("GET/user/sign-out", new ReportViewController());
    }

    public static ControllerAction getAction(HttpServletRequest request) {
        return actions.get(request.getMethod() + request.getPathInfo());
    }
}
