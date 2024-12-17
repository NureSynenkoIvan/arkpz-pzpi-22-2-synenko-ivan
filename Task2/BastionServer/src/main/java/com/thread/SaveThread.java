package com.thread;

import com.model.SkyState;
import com.service.database.DatabaseService;
import com.service.database.dao.SkyStateDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SaveThread extends Thread {
    private static final int MAX_BUFFER_SIZE = 3; //For test reasons
    private final SkyStateDao skyStateDao;
    private final BlockingQueue<SkyState> saveQueue;
    private final ScheduledExecutorService scheduler;

    public SaveThread(BlockingQueue<SkyState> saveQueue) {
        this.skyStateDao = DatabaseService.getInstance().getSkyStateDao();
        this.saveQueue = saveQueue;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        System.out.println("Save Thread started");
        scheduler.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            if (saveQueue.size() > MAX_BUFFER_SIZE) {
                List<SkyState> buffer = new ArrayList<>();
                saveQueue.drainTo(buffer);
                skyStateDao.saveAll(buffer);
                System.out.println("Saved " + buffer.size() + " states to the database.");
            } else {
                System.out.println("Current buffer size is " + saveQueue.size() + ", thread goes back to sleep");
            }
        } catch (Exception e) {
            System.err.println("Error during save operation: " + e.getMessage());
        }
    }
}