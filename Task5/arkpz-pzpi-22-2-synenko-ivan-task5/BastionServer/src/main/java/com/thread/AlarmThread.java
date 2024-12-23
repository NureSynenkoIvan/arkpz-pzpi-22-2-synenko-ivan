package com.thread;

import com.BastionApplication;
import com.events.AlarmEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class AlarmThread extends Thread {
    private static final int TIME_UNTIL_AUTO_ALARM = 10000;
    private final BlockingQueue<AlarmEvent> alarmsQueue;

    private AtomicBoolean isSetManually;

    public AlarmThread(BlockingQueue<AlarmEvent> alarmsQueue) {
        this.alarmsQueue = alarmsQueue;
        isSetManually = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Wait for the alarm
                AlarmEvent event = alarmsQueue.take();

                //If alarm isn't already started - wait for manual input (Currently not realised).
                if (! BastionApplication.isAlarm()) {
                    isSetManually.set(false);
                    System.out.println("Object considered dangerous! Alarm has been triggered!");
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Waiting for alarm!");
                        this.sleep(1000);
                    }

                    //And start the alarm.
                    System.out.println("Starting air alert! Notifying application!");
                    BastionApplication.startAlarm(event);
                } else {
                    //This represents showing info about new threat.
                    System.out.println("New alarming object!: " + event.toString());
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
        isSetManually.set(true);
    }
}
