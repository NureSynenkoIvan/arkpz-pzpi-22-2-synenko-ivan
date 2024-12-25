package com.web.singleton;

import com.model.SkyState;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/// Singleton queue that's used to collect data
/// from radars and send it to RadarAnalyzerThread.

public class SkyStateQueueSingleton {
    private static final int SKY_STATE_QUEUE_SIZE = 10;
    private static SkyStateQueueSingleton instance;
    BlockingQueue<SkyState> queue;


    private SkyStateQueueSingleton() {
        queue = new ArrayBlockingQueue<>(SKY_STATE_QUEUE_SIZE);
    }

    public static SkyStateQueueSingleton getInstance() {
        if (instance == null) {
            instance = new SkyStateQueueSingleton();
        }
        return instance;
    }


    public void poll() {
        queue.poll();
    }

    public SkyState take() throws InterruptedException {
        return queue.take();
    }

    public void put(SkyState currentSkyState) throws InterruptedException {
        queue.put(currentSkyState);
    }
}
