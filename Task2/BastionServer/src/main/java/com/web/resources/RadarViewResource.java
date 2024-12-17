package com.web.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/radar")
public class RadarViewResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAllEmployees() {
        return "<h1>REAL-TIME RADAR VIEW IS YET TO BE IMPLEMENTED</h1> <br> Thanks for your patience!";
    }
}
