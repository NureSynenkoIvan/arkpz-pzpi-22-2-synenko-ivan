package com.service.notification.listener;

import com.events.AlarmEvent;

public interface Notifier {
    public void sendNotification(AlarmEvent event);
}
