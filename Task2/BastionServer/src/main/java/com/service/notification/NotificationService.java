package com.service.notification;

import com.events.AlarmEvent;
import com.service.notification.listener.Notifier;
import com.service.notification.listener.impl.MobileNotifier;
import com.service.notification.listener.impl.RadioNotifier;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private static NotificationService instance;
    private List<Notifier> notifierList;

    private NotificationService() {
        notifierList = List.of(
                new RadioNotifier(),
                new MobileNotifier()
        );
    }


    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendNotifications(AlarmEvent event) {
        for (Notifier notifier : notifierList) {
            notifier.sendNotification(event);
        }
    }
}
