package com.thread;

import com.web.resources.RadarReceiverResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadarDataCollectorThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(RadarDataCollectorThread.class);

    public RadarDataCollectorThread() {
    }

    public void start() {
        logger.info("Radar Data Collector started");
    }

    @Override
    public void run() {
        //This thread puts information about sky to the analyzer.
        //Should be called periodically to ensure that listening resource does not wait indefinitely.

        try {
            if (RadarReceiverResource.listeningStarted()) {
                RadarReceiverResource.tryPutToQueue();
                logger.debug("Put state to the queue, though not all radars have reported.");
            } else {
                logger.debug("RadarReceiverResource listening not started");
            }
        } catch (Exception e) {
            logger.error("Error during save operation", e);
        }
    }

}
