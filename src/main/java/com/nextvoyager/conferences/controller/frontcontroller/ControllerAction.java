package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface for a controller action. Contains constants with action paths.
 *
 * @author Stanislav Bozhevskyi
 */
public interface ControllerAction {

    String PREFIX_PATH = "/pages";

    String HOME = "/home";
    String ABOUT = "/about";
    String CHANGE_LANGUAGE = "/change-language";

    String EVENT_CREATE = "/event/create";
    String EVENT_DELETE = "/event/delete";
    String EVENT_EDIT = "/event/edit";
    String EVENT_REGISTER = "/event/register";
    String EVENT_STATISTICS = "/event/statistics";
    String EVENT_SAVE_STATISTICS = "/event/save-statistics";
    String EVENT_VIEW = "/event/view";
    String EVENT_LIST_FILTER = "/event/list-filter";
    String EVENT_LIST_SORT = "/event/list-sort";
    String EVENT_LIST_VIEW_FORM = "/event/list-view-form";

    String REPORT_CREATE = "/report/create";
    String REPORT_DELETE = "/report/delete";
    String REPORT_EDIT = "/report/edit";
    String REPORT_LIST = "/report/list";
    String REPORT_VIEW = "/report/view";
    String REPORT_LIST_FILTER = "/report/list-filter";

    String USER_LOGIN = "/user/login";
    String USER_PROFILE = "/user/profile";
    String USER_REGISTRATION = "/user/registration";
    String USER_CHANGE_PASSWORD = "/user/change-password";
    String USER_FORGOT_PASSWORD = "/user/forgot-password";
    String USER_RESET_PASSWORD = "/user/reset-password";
    String USER_SIGN_OUT = "/user/sign-out";
    String MODERATOR_REPORT_APPROVAL = "/moderator-report-action";
    String SPEAKER_REPORT_APPROVAL = "/speaker-report-action";
    String USER_LIST = "/user/list";
    String USER_SHOW_MESSAGE = "/user/show-message";

    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
