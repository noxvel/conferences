package com.nextvoyager.conferences.service.notification;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.util.emailcreator.EmailCreator;

/**
 * Utility class for work with notifications for report changes
 *
 * @author Stanislav Bozhevskyi
 */
public class ReportNotificationManager {

    private ReportNotificationManager(){};

    public static void notifySpeaker(User speaker, Report report, String notifyMsg) {
        if (speaker.getReceiveNotifications()) {
            EmailCreator.send(speaker.getEmail(), prepareNotificationSubject(report), notifyMsg);
        }
    }
    private static String prepareNotificationSubject(Report report) {
        return "Status of the report '" + report.getTopic() + "' changed";
    }
}
