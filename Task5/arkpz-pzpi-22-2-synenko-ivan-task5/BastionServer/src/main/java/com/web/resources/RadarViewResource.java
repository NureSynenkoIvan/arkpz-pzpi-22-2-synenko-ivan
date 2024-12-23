package com.web.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
/***************************************

 Resource that will be used to view
 radar view from viewer threads.

 **************************************/

@Path("/radar")
public class RadarViewResource {
    @GET
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.TEXT_HTML)
    public String getAllEmployees() {
        return "<h1>REAL-TIME RADAR VIEW IS YET TO BE IMPLEMENTED</h1> <br> Thanks for your patience!";
    }
}
