package com.thread;

import com.model.SkyState;
import com.service.database.DatabaseService;
import com.service.database.dao.SkyStateDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SaveThread extends Thread {
    private static final int MAX_BUFFER_SIZE = 3; //For test reasons, can be any reasonable number
    private final SkyStateDao skyStateDao;
    private final BlockingQueue<SkyState> saveQueue;

    public SaveThread(BlockingQueue<SkyState> saveQueue) {
        this.skyStateDao = DatabaseService.getInstance().getSkyStateDao();
        this.saveQueue = saveQueue;
    }

    public void start() {
    }

    @Override
    public void run() {
        //It better be called periodically to avoid wasting system resoutces.
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

    public void saveForcefully() {
        if (saveQueue.size() > 0) {
            List<SkyState> buffer = new ArrayList<>();
            saveQueue.drainTo(buffer);
            skyStateDao.saveAll(buffer);
            System.out.println("Saved " + buffer.size() + " states to the database.");
        }
    }
}