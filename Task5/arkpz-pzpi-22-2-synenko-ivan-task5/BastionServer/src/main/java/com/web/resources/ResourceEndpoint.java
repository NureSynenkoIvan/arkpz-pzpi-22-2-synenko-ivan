package com.web.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


//A test resource for authentication testing purposes.

@Path("/resource")
@Deprecated
public class ResourceEndpoint {

    @GET
    @RolesAllowed({"dispatcher", "administrator"})
    public Response getResource() {
        return Response.ok("GET: Resource for dispatcher and admin").build();
    }

    @POST
    @RolesAllowed("administrator")
    public Response createResource(String body) {
        return Response.ok("POST: Resource created").build();
    }

    @PUT
    @RolesAllowed("administrator")
    public Response updateResource(String body) {
        return Response.ok("PUT: Resource updated").build();
    }

    @DELETE
    @RolesAllowed("administrator")
    public Response deleteResource() {
        return Response.ok("DELETE: Resource deleted").build();
    }
}