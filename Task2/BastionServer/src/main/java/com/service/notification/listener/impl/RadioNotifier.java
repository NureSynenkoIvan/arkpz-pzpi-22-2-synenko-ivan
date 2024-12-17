package com.service.notification.listener.impl;


import com.events.AlarmEvent;
import com.service.notification.listener.Notifier;

public class RadioNotifier implements Notifier {

    @Override
    public void sendNotification(AlarmEvent event) {
        System.out.println("Radio Notifier is yet to be implemented");
    }
}
