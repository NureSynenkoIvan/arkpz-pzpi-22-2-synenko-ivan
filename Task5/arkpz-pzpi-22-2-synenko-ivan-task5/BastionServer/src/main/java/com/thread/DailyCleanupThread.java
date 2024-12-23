package com.thread;

import com.service.database.DatabaseService;

public class DailyCleanupThread implements Runnable {
    private static final String OUTPUT_FILE_PATH = "C:\\Users\\HP\\OneDrive\\Desktop\\MongoDB";

    @Override
    public void run() {
        try {
            System.out.println("Performing daily cleanup...");
            DatabaseService.getInstance().dumpSkyStates(OUTPUT_FILE_PATH);
            System.out.println("Sky states dumped successfully.");
        } catch (Exception e) {
            System.err.println("Error during daily cleanup: " + e.getMessage());
        }
    }
}