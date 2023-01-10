package com.nextvoyager.conferences.service.notification;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.util.EmailSender;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class EventNotificationManager {

    public static void notify(List<User> userList, Event event, NotificationType type) {
        List<String> sendToEmails = userList.stream().map(User::getEmail).collect(Collectors.toList());
        EmailSender.send(sendToEmails, prepareNotificationSubject(event,type), prepareNotificationText(event,type));
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
