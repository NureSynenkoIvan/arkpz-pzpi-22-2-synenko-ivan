package com.thread;

import com.model.SkyState;
import com.service.database.DatabaseService;
import com.service.database.dao.SkyStateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SaveThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(SaveThread.class);
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
        //It better be called periodically to avoid wasting system resources.
        try {
            if (saveQueue.size() > MAX_BUFFER_SIZE) {
                List<SkyState> buffer = new ArrayList<>();
                saveQueue.drainTo(buffer);
                skyStateDao.saveAll(buffer);
                logger.info("Saved {} states to the database.", buffer.size());
            } else {
                logger.debug("Current buffer size is {}, thread goes back to sleep", saveQueue.size());
            }
        } catch (Exception e) {
            logger.error("Error during save operation", e);
        }
    }

    public void saveForcefully() {
        if (saveQueue.size() > 0) {
            List<SkyState> buffer = new ArrayList<>();
            saveQueue.drainTo(buffer);
            skyStateDao.saveAll(buffer);
            logger.info("Forcefully saved {} states to the database while buffer was not full.", buffer.size());
        }
    }
}