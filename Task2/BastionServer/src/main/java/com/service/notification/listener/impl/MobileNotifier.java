package com.service.notification.listener.impl;

import com.events.AlarmEvent;
import com.service.notification.listener.Notifier;


public class MobileNotifier implements Notifier {
    @Override
    public void sendNotification(AlarmEvent event) {
        System.out.println("Mobile notifications will be implemented with the mobile release");
    }
}
