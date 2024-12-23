package com.web.resources;

import com.events.AlarmEvent;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.BastionApplication.startAlarm;
import static com.BastionApplication.stopAlarm;

/***************************************
 Resource used for manually starting
 and stopping alarm.
 **************************************/


@Path("/alarm")
public class AlarmResource {
    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"administrator"})
    public Response manuallyStartAlarm(AlarmEvent alarmEvent) {
        try {
            startAlarm(alarmEvent);
            return Response.ok("Alarm started successfully with the provided alert event.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to start alarm: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/stop")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"administrator"})
    public Response manuallyStopAlarm() {
        try {
            stopAlarm();
            return Response.ok("Alarm stopped successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to stop alarm: " + e.getMessage())
                    .build();
        }
    }
}