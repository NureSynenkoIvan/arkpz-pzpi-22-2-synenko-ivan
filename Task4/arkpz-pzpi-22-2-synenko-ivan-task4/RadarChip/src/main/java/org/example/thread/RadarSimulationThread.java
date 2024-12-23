package org.example.thread;

import org.example.model.RadarCompletedSpinning;
import org.example.model.RadarInformation;
import org.example.model.RadarObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class RadarSimulationThread extends Thread {
    private static final float PI = 3.14f;
    private final BlockingQueue<RadarInformation> objectsInSky;
    private volatile boolean radarSpinning;

    public RadarSimulationThread(final BlockingQueue<RadarInformation> objectsInSky) {
        this.objectsInSky = objectsInSky;
        radarSpinning = false;
    }

    public boolean isRadarSpinning() {
        return radarSpinning;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //GENERATE RADAR OBJECTS
                radarSpinning = true;

                // Simulate radar object generation
                for (int i = 0; i < 10; i++) { // Generate 10 objects per spin
                    RadarObject radarObject = new RadarObject(
                            ThreadLocalRandom.current().nextFloat(1, 100),
                            ThreadLocalRandom.current().nextFloat(0, PI * 2),
                            ThreadLocalRandom.current().nextFloat(0, PI),
                            ThreadLocalRandom.current().nextFloat(0, PI),
                            ThreadLocalRandom.current().nextFloat(0, PI),
                            ThreadLocalRandom.current().nextFloat(0, 400),
                            true
                    );
                    objectsInSky.put(radarObject);
                    System.out.println("Object seen");
                }

                // Simulate radar completing a full spin
                Thread.sleep(1000); // Simulate spin time
                objectsInSky.put(new RadarCompletedSpinning());
                System.out.println("Radar completed spinning");
                radarSpinning = false;

                // Wait briefly before next spin cycle
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }
}
