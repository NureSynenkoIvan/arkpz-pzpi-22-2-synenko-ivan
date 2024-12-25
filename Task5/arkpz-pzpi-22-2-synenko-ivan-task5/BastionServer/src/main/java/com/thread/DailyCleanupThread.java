package com.thread;

import com.service.database.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DailyCleanupThread implements Runnable {
    Logger logger = LoggerFactory.getLogger(DailyCleanupThread.class);
    private static final String OUTPUT_FILE_PATH = "C:\\Users\\HP\\OneDrive\\Desktop\\MongoDB";

    @Override
    public void run() {
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            logger.info("Performing daily cleanup...");

            dbService.dumpSkyStates(OUTPUT_FILE_PATH);
            logger.info("Saved dump of all sky states of this day");

            logger.info("Cleaning all sky states of this day");
            dbService.getSkyStateDao().deleteAll();
            logger.info("Cleanup complete.");

            dbService.dumpDatabase(OUTPUT_FILE_PATH);
            logger.info("Database dumped successfully.");


        } catch (Exception e) {
            logger.error("Error during daily cleanup: " + e.getMessage());
        }
    }
}