package com.thread;

import com.web.resources.RadarReceiverResource;

public class RadarDataCollectorTread extends Thread {

    public RadarDataCollectorTread() {
    }

    public void start() {
        System.out.println("Radar Data Collector started");
    }

    @Override
    public void run() {
        //This thread puts information about sky to the analyzer.
        //Should be called periodically to ensure that listening resource does not wait indefinitely.

        try {
            if (RadarReceiverResource.listeningStarted()) {
                RadarReceiverResource.tryPutToQueue();
                System.out.println("RadarDataCollector put state to the queue, though not all radars have reported.");
            } else {
                System.out.println("RadarDataCollector: RadarReceiverResource listening not started");
            }
        } catch (Exception e) {
            System.err.println("Error during save operation: " + e.getMessage());
        }
    }

}
