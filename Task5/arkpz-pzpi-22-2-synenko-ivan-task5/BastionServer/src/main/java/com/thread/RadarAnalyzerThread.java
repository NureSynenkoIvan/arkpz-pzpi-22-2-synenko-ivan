package com.thread;

import com.events.AlarmEvent;
import com.model.SkyObject;
import com.model.SkyState;
import com.strategy.MockStrategy;
import com.strategy.ThreatAnalysisStrategy;
import com.web.singleton.SkyStateQueueSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RadarAnalyzerThread extends Thread {
    Logger logger = LoggerFactory.getLogger(RadarAnalyzerThread.class);

    private final SkyStateQueueSingleton skyStatesQueue;
    private final BlockingQueue<SkyState> viewsQueue;
    private final BlockingQueue<SkyState> saveQueue;
    private final BlockingQueue<AlarmEvent> alarmsQueue;
    private final ThreatAnalysisStrategy strategy;

    private SkyState currentSkyState;

    public RadarAnalyzerThread(BlockingQueue<SkyState> viewsQueue,
                               BlockingQueue<SkyState> saveQueue,
                               BlockingQueue<AlarmEvent> alarmsQueue) {
        this.skyStatesQueue = SkyStateQueueSingleton.getInstance();
        this.viewsQueue = viewsQueue;
        this.saveQueue = saveQueue;
        this.alarmsQueue = alarmsQueue;

        this.strategy = new MockStrategy();
    }

    @Override
    public void run() {
        logger.info("RadarAnalyzerThread started");
        try {
            while (true) {
                currentSkyState = skyStatesQueue.take();
                logger.info("RadarAnalyzerThread received skyState");

                //Saving data to DB and viewing is delegated to different threads.
                viewsQueue.put(currentSkyState);
                saveQueue.put(currentSkyState);

                //Analysis is delegated to strategy, so we can change it without changing this file.
                //Get list of threatening objects.
                List<SkyObject> threateningObjects = strategy.analyze(currentSkyState);
                logger.info("RadarAnalyzerThread analyzed skyState");

                if (! threateningObjects.isEmpty()) {
                    AlarmEvent alarmEvent = new AlarmEvent(threateningObjects);
                    alarmsQueue.put(alarmEvent);
                }


            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
