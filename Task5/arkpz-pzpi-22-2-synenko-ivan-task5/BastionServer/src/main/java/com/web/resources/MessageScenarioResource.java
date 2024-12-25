package com.web.resources;

import com.model.MessageScenario;
import com.service.database.DatabaseService;
import com.service.database.dao.MessageScenarioDao;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/***************************************

 Resource used to access message_scenarios collection
 in database and perform CRUD operations on it.

 **************************************/


@Path("/message-scenarios")
public class MessageScenarioResource {
    private final MessageScenarioDao messageScenarioDao
            = DatabaseService
            .getInstance()
            .getMessageScenarioDao();

    @GET
    @Path("/view")
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public MessageScenario getMessageScenario(@QueryParam("scenarioName") String scenarioName) {
        return messageScenarioDao.get(scenarioName);
    }

    @GET
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageScenario> getAllEmployees() {
        return messageScenarioDao.getAll( );
    }

    @POST
    @RolesAllowed({"administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMessageScenario(MessageScenario scenario) {
        messageScenarioDao.save(scenario);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @RolesAllowed({"administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMessageScenario(MessageScenario scenario) {
        messageScenarioDao.update(scenario);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @RolesAllowed({"administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMessageScenario(MessageScenario scenario) {
        messageScenarioDao.delete(scenario);
        return Response.status(Response.Status.OK).build();
    }

}
