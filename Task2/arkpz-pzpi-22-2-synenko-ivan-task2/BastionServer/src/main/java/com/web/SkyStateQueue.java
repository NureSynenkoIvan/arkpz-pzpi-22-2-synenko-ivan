package com.web;

import com.model.SkyState;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SkyStateQueue {
    private static final int SKY_STATE_QUEUE_SIZE = 10;
    private static SkyStateQueue instance;
    BlockingQueue<SkyState> queue;


    private SkyStateQueue() {
        queue = new ArrayBlockingQueue<>(SKY_STATE_QUEUE_SIZE);
    }

    public static SkyStateQueue getInstance() {
        if (instance == null) {
            instance = new SkyStateQueue();
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
