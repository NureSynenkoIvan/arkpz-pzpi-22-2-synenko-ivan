package com.thread;

import com.events.AlarmEvent;
import com.service.notification.NotificationService;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class AlarmThread extends Thread {
    private static final int TIME_UNTIL_AUTO_ALARM = 10000;
    private final BlockingQueue<AlarmEvent> alarmsQueue;
    private final NotificationService notificationService;

    private AtomicBoolean isSetManually;

    public AlarmThread(BlockingQueue<AlarmEvent> alarmsQueue) {
        this.alarmsQueue = alarmsQueue;
        this.notificationService = NotificationService.getInstance();
        isSetManually = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        try {
            while (true) {
                AlarmEvent event = alarmsQueue.take();
                isSetManually.set(false);
                System.out.println("Object considered dangerous! Alarm has been triggered!");

                // Wait for 10 seconds with a possibility to be manually triggered
                synchronized (isSetManually) {
                    long startTime = System.currentTimeMillis();
                    while (!isSetManually.get() &&
                            System.currentTimeMillis() - startTime < TIME_UNTIL_AUTO_ALARM) {
                        // Wait with timeout to allow checking manual trigger
                        System.out.println("Waiting for alarm!");
                        isSetManually.wait(TIME_UNTIL_AUTO_ALARM / 10);
                    }
                    System.out.println("Starting air alert!");
                    notificationService.sendNotifications(event);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public AtomicBoolean getIsSetManually() {
        return isSetManually;
    }

    public void setIsSetManually(AtomicBoolean isSetManually) {
        this.isSetManually = isSetManually;
    }

    public void setManualAlarm() {
        synchronized (isSetManually) {
            isSetManually.set(true);
            notificationService.notifyAll();
        }
    }
}
