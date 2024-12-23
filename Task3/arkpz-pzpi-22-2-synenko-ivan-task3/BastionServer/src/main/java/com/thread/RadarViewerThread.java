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
        //This thread is needed for demonstrating the state of sky.
        //It will be used by the web app to create real-time view.

        System.out.println("RadarViewerThread started");
        while (true) {
            try {
                currentSkyState = viewsQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //This represents radar receiving.
            System.out.println("RadarViewerThread received skyState");
            System.out.println("Showing skyState:" + currentSkyState.toString());
        }
    }
}
