package com.web.resources;


import com.config.DevicesConfig;
import com.model.Device;
import com.model.SkyObject;
import com.model.SkyState;
import com.model.enums.DeviceType;
import com.web.SkyStateQueue;
import com.web.codec.FasterXMLCodecStrategy;
import com.web.codec.RadarCodecStrategy;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

/***************************************

 Resource used to collect data from the radars.
 Currently, radars send data in JSON format to
 this address via POST requests.

 It decodes data, collects it into a SkyState
 and when all radars have reported, puts info
 to queue to analyze.

 **************************************/

@Path("/radar")
@Singleton
public class RadarReceiverResource {
    private static final long MAX_WAIT_TIME = 2000;
    private static final RadarCodecStrategy codecStrategy = new FasterXMLCodecStrategy();
    private static final DevicesConfig devicesConfig = DevicesConfig.getInstance();
    private static List<Device> activeRadars = devicesConfig.getActiveDevices().stream()
            .filter(device -> device.getType().equals(DeviceType.RADAR))
            .toList();
    private static volatile AtomicInteger radarsLeft = new AtomicInteger(activeRadars.size());;
    private static final SkyStateQueue skyStateQueue = SkyStateQueue.getInstance();
    private static Map<Integer, List<SkyObject>> currentRadarData = new ConcurrentHashMap<>();
    private static volatile AtomicBoolean listeningStarted =  new AtomicBoolean(false);


    public RadarReceiverResource() {
        for (Device radar : activeRadars) {
            if (!currentRadarData.containsKey(radar.getDeviceId())) {
                currentRadarData.put(radar.getDeviceId(), new ArrayList<>());
            }
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

        listeningStarted.set(true);
        try {
            SkyObject[] received = codecStrategy.decodeRadarData(json);



            Integer receivedId = received[0].deviceID;
            if (currentRadarData.get(receivedId).isEmpty()) {
                radarsLeft.decrementAndGet();
            }
            currentRadarData.put(receivedId, List.of(received));

            if (radarsLeft.get() == 0 && listeningStarted.get() == true) {
                tryPutToQueue();
            }

            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public static synchronized void tryPutToQueue() {
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

            stopListening();
        }
    }

    public static synchronized void stopListening() {
        listeningStarted.set(false);
        radarsLeft = new AtomicInteger(activeRadars.size());
        currentRadarData.replaceAll((key, value) -> new ArrayList<>());
    }

    public static boolean listeningStarted() {
        return listeningStarted.get();
    }
}
