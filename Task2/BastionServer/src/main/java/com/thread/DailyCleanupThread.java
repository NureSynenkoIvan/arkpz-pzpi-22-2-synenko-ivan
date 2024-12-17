package com.thread;

import com.service.database.DatabaseService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyCleanupThread implements Runnable {
    private final ScheduledExecutorService scheduler;

    public DailyCleanupThread() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        System.out.println("Daily Cleanup Thread started");
        scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.DAYS);
    }

    @Override
    public void run() {
        try {
            System.out.println("Performing daily cleanup...");
            DatabaseService.getInstance().dumpSkyStates();
            System.out.println("Sky states dumped successfully.");
        } catch (Exception e) {
            System.err.println("Error during daily cleanup: " + e.getMessage());
        }
    }

    public void stop() {
        scheduler.shutdown();
    }
}