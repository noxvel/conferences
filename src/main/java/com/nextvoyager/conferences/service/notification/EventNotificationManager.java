package com.nextvoyager.conferences.service.notification;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.util.emailcreator.EmailCreator;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for work with notifications for event changes
 *
 * @author Stanislav Bozhevskyi
 */
public class EventNotificationManager {

    private EventNotificationManager(){};

    public static void notify(List<User> userList, Event event, NotificationType type) {
        if (!userList.isEmpty()) {
            String sendToEmails = userList.stream()
                    .map(User::getEmail)
                    .collect(Collectors.joining(","));
            EmailCreator.send(sendToEmails, prepareNotificationSubject(event,type), prepareNotificationText(event,type));
        }
    }

    private static String prepareNotificationText(Event event, NotificationType type) {
        return "Event '" + event.getName() + "', in which you participate, was " + type.getNotificationText();
    }

    private static String prepareNotificationSubject(Event event, NotificationType type) {
        return "Changes in event '" + event.getName() + "'";
    }

    @Getter
    public enum NotificationType{
        UPDATE("updated"),
        DELETE("deleted");

        private final String notificationText;

        NotificationType(String notificationText) {
            this.notificationText = notificationText;
        }
    }
}
