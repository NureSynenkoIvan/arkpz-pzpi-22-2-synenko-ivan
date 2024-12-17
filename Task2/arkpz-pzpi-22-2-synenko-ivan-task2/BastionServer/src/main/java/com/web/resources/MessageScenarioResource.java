package com.web.resources;

import com.model.MessageScenario;
import com.service.database.DatabaseService;
import com.service.database.dao.MessageScenarioDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/message-scenarios")
public class MessageScenarioResource {
    private MessageScenarioDao messageScenarioDao;

    public MessageScenarioResource() {
        DatabaseService databaseService = DatabaseService.getInstance();
        this.messageScenarioDao = databaseService.getMessageScenarioDao();
    }

    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageScenario getEmployee(@QueryParam("scenarioName") String scenarioName) {
        return messageScenarioDao.get(scenarioName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageScenario> getAllEmployees() {
        return messageScenarioDao.getAll( );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMessageScenario(MessageScenario scenario) {
        messageScenarioDao.save(scenario);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMessageScenario(MessageScenario scenario) {
        messageScenarioDao.update(scenario);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    public Response deleteMessageScenario(MessageScenario scenario) {
        messageScenarioDao.delete(scenario);
        return Response.status(Response.Status.OK).build();
    }

}
