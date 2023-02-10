package com.nextvoyager.conferences.controller.actions;

/**
 * Different constant fields for controller actions(parameters, etc.).
 *
 * @author Stanislav Bozhevskyi
 */
public interface ControllerActionConstants {
    String PARAM_USER_FIRST_NAME = "firstName";
    String PARAM_USER_LAST_NAME = "lastName";
    String PARAM_USER_EMAIL = "email";
    String PARAM_USER_PASSWORD = "password";
    String PARAM_USER_ROLE = "userRole";
    String PARAM_USER_APPROVAL_ACTION = "action";
    String PARAM_USER_NEW_PASSWORD = "newPassword";
    String PARAM_USER_CURRENT_PASSWORD = "currentPassword";
    String PARAM_USER_RECEIVE_NOTIFICATIONS = "receiveNotifications";
    String PARAM_USER_RECAPTCHA = "g-recaptcha-response";

    String PARAM_EVENT_ID = "eventID";
    String PARAM_EVENT_NAME = "name";
    String PARAM_EVENT_PLACE = "place";
    String PARAM_EVENT_BEGIN_DATE = "beginDate";
    String PARAM_EVENT_END_DATE = "endDate";
    String PARAM_EVENT_DESCRIPTION = "description";
    String PARAM_EVENT_PARTICIPANTS_CAME = "participantsCame";
    String PARAM_EVENT_REGISTER_USER = "register";
    String PARAM_EVENT_SEND_NOTIFICATION = "sendNotification";

    String PARAM_REPORT_ID = "reportID";
    String PARAM_REPORT_TOPIC = "topic";
    String PARAM_REPORT_SPEAKER = "speaker";
    String PARAM_REPORT_EVENT = "event";
    String PARAM_REPORT_STATUS = "status";
    String PARAM_REPORT_DESCRIPTION = "description";
}
