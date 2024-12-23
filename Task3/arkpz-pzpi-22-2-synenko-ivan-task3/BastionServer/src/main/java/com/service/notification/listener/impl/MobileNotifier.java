package com.service.notification.listener.impl;

import com.events.AlarmEvent;
import com.service.notification.listener.Notifier;


public class MobileNotifier implements Notifier {
    @Override
    public void sendAlarmNotification(AlarmEvent event) {
        System.out.println("Alarm notifications sent! \nMobile notifications will be implemented with the mobile release");
    }

    @Override
    public void sendStopAlarmNotification() {
        System.out.println("All clear alarm notifications sent! \nMobile notifications will be implemented with the mobile release");
    }
}
