package com.web.resources;

import com.service.database.DatabaseService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/***************************************

 Resource used to manually make DB backups.

 **************************************/

@Path("/dump")
public class DatabaseDumpResource {
    private static final String OUTPUT_FILE_PATH = "C:\\Users\\HP\\OneDrive\\Desktop\\MongoDB";
    private static final DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Path("/sky-states")
    @RolesAllowed({"administrator"})
    public Response dumpSkyStates() {
        databaseService.dumpSkyStates(OUTPUT_FILE_PATH);
        return Response.ok().build();
    }

    @POST
    @Path("/all")
    @RolesAllowed({"administrator"})
    public Response dumpAll() {
        databaseService.dumpDatabase(OUTPUT_FILE_PATH);
        return Response.ok().build();
    }


}
