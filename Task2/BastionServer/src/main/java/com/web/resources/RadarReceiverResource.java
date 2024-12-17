package com.web.resources;


import com.config.DevicesConfig;
import com.model.Device;
import com.model.SkyObject;
import com.model.SkyState;
import com.model.enums.DeviceType;
import com.web.SkyStateQueue;
import com.web.codec.FasterXMLCodecStrategy;
import com.web.codec.RadarCodecStrategy;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;


@Path("/radar")
public class RadarReceiverResource {
    private static final long MAX_WAIT_TIME = 1000;
    private static final RadarCodecStrategy codecStrategy = new FasterXMLCodecStrategy();
    private static final SkyStateQueue skyStateQueue = SkyStateQueue.getInstance();
    private static final DevicesConfig devicesConfig = DevicesConfig.getInstance();
    private static List<Device> activeRadars = devicesConfig.getActiveDevices().stream()
            .filter(device -> device.getType().equals(DeviceType.RADAR))
            .toList();
    private static volatile AtomicInteger radarsLeft = new AtomicInteger(activeRadars.size());;
    private static volatile AtomicBoolean listeningStarted =  new AtomicBoolean(false);
    private Map<Integer, List<SkyObject>> currentRadarData = new ConcurrentHashMap<>();
    ExecutorService countdownExecutor = Executors.newSingleThreadExecutor();


    public RadarReceiverResource() {
        currentRadarData = new ConcurrentHashMap<>();
        for (Device radar : activeRadars) {
            currentRadarData.put(radar.getDeviceId(), new ArrayList<>());
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/receive")
    public String receiveTest() {
        return "<h1>THIS IS RADAR RECEIVER</h1>";
    }

    @POST
    @Path("/receive")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response receiveRadarData(String json) {
        if (listeningStarted.compareAndSet(false, true)) {
            synchronized (this) {
                //countdownExecutor.execute(new CountDownTask());
            }
        }
        //TODO: THIS BREAKS IF LIST IS NOT CONSISTING OF SKY OBJECTS, FIX
        SkyObject[] received = codecStrategy.decodeRadarData(json);
        Integer receivedId = received[0].deviceID;
        if (currentRadarData.get(receivedId).isEmpty()) {
            radarsLeft.decrementAndGet();
        }
        currentRadarData.put(receivedId, List.of(received));

        if (radarsLeft.get() == 0 && listeningStarted.get() == true) {
            putToSkyStatesQueue();
        }

        return Response.status(Response.Status.OK).build();
    }

    private synchronized void putToSkyStatesQueue() {
        if (listeningStarted.get() == true) {
            List<SkyObject> collectedData = currentRadarData.values().stream()
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .toList();
            SkyState currentSkyState = new SkyState(new Date(), false, collectedData);
            try {
                skyStateQueue.put(currentSkyState);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            listeningStarted.set(false);
            radarsLeft = new AtomicInteger(activeRadars.size());
            currentRadarData.replaceAll((key, value) -> new ArrayList<>());
        }
    }

    private class CountDownTask implements Runnable {
        @Override
        public void run() {
            try {
                sleep(MAX_WAIT_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (listeningStarted.compareAndSet(true, false)) {
                    putToSkyStatesQueue();
                }
            }
        }
    }

}
