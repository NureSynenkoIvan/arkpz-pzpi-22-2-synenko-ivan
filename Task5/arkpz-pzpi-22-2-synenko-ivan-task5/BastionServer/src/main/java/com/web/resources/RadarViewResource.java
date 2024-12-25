package com.web.resources;

import com.model.SkyState;
import com.thread.RadarViewerThread;
import com.web.singleton.RadarViewerThreadSingleton;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
/***************************************

 Resource that will be used to view
 radar view from viewer threads.

 **************************************/

@Path("/radar-view")
public class RadarViewResource {
    private final RadarViewerThread radarViewerThread = RadarViewerThreadSingleton.getInstance();

    @GET
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.TEXT_HTML)
    public String getRadarView() {
        return "<h1>REAL-TIME RADAR VIEW IS YET TO BE IMPLEMENTED</h1> <br> Thanks for your patience!";
    }

    @GET
    @Path("/json")
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public SkyState getRadarViewJSON() {
        return radarViewerThread.getCurrentSkyState();
    }
}
