package com.thread;

import com.model.SkyState;

import java.util.concurrent.BlockingQueue;

public class RadarViewerThread extends Thread {
    private final BlockingQueue<SkyState> viewsQueue;

    private SkyState currentSkyState;

    public RadarViewerThread(BlockingQueue<SkyState> viewsQueue) {
        this.viewsQueue = viewsQueue;
    }

    @Override
    public void run() {
        System.out.println("RadarViewerThread started");
        while (true) {
            try {
                currentSkyState = viewsQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("RadarViewerThread received skyState");
            System.out.println("Showing skyState:" + currentSkyState.toString());
        }
    }
}
