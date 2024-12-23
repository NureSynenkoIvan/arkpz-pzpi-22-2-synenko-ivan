package org.example.thread;

import org.example.codec.ServerDataTransmissionStrategy;
import org.example.model.RadarCompletedSpinning;
import org.example.model.RadarInformation;
import org.example.model.RadarObject;
import org.example.model.server.SkyObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RadarBridge extends Thread {
    private final RadarSimulationThread simulation;
    private final ServerDataTransmissionStrategy strategy;
    private final BlockingQueue<RadarInformation> queue;
    private final String serverUrl;
    private List<SkyObject> skyObjects;

    public RadarBridge(RadarSimulationThread simulation, BlockingQueue<RadarInformation> queue, String serverUrl) {
        this.simulation = simulation;
        this.queue = queue;
        this.serverUrl = serverUrl;
        this.strategy = new ServerDataTransmissionStrategy() {
        };
        this.skyObjects = new ArrayList<SkyObject>();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                RadarInformation object = queue.take();

                if (!(object instanceof RadarCompletedSpinning)) {
                    skyObjects.add(((RadarObject) object).toCartesianCoordinates());
                } else {
                    String json = (String) strategy.encode(skyObjects);
                    System.out.println("Sending : " + json);
                    strategy.sendToServer(json, serverUrl);
                    skyObjects.clear();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
