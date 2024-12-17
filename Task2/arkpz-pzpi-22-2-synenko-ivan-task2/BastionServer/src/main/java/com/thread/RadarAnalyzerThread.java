package com.thread;

import com.BastionApplication;
import com.events.AlarmEvent;
import com.model.SkyObject;
import com.model.SkyState;
import com.strategy.MockStrategy;
import com.strategy.ThreatAnalysisStrategy;
import com.web.SkyStateQueue;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RadarAnalyzerThread extends Thread {
    private final SkyStateQueue skyStatesQueue;
    private final BlockingQueue<SkyState> viewsQueue;
    private final BlockingQueue<SkyState> saveQueue;
    private final BlockingQueue<AlarmEvent> alarmsQueue;
    private final ThreatAnalysisStrategy strategy;

    private SkyState currentSkyState;

    public RadarAnalyzerThread(BlockingQueue<SkyState> viewsQueue,
                               BlockingQueue<SkyState> saveQueue,
                               BlockingQueue<AlarmEvent> alarmsQueue) {
        this.skyStatesQueue = SkyStateQueue.getInstance();
        this.viewsQueue = viewsQueue;
        this.saveQueue = saveQueue;
        this.alarmsQueue = alarmsQueue;

        this.strategy = new MockStrategy();
    }

    @Override
    public void run() {
        System.out.println("RadarAnalyzerThread started");
        try {
            while (true) {
                currentSkyState = skyStatesQueue.take();
                System.out.println("RadarAnalyzerThread received skyState");
                viewsQueue.put(currentSkyState);
                saveQueue.put(currentSkyState);
                //Analysis is delegated to strategy, so we can change it without changing this file.
                List<SkyObject> threateningObjects = strategy.analyze(currentSkyState);
                System.out.println("RadarAnalyzerThread analyzed skyState");

                if (! threateningObjects.isEmpty()) {
                    AlarmEvent alarmEvent = new AlarmEvent(threateningObjects);
                    alarmsQueue.put(alarmEvent);
                    BastionApplication.startAlarm();
                }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
