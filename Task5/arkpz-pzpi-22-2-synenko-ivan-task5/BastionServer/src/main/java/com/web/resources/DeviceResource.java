package com.web.resources;

import com.model.Device;
import com.model.enums.SortingOrder;
import com.service.database.DatabaseService;
import com.service.database.dao.DeviceDao;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/***************************************

 Resource used to access devices collection
 in database and perform CRUD operations on it.

 **************************************/


@Path("/devices")
public class DeviceResource {
    private final DeviceDao deviceDao =
            DatabaseService
                    .getInstance()
                    .getDeviceDao();

    @GET
    @Path("/view")
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@QueryParam("name") String name) {
        return deviceDao.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"dispatcher", "administrator"})
    public List<Device> getAllDevices() {
        return deviceDao.getAll(SortingOrder.ASC);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"administrator"})
    public Response addDevice(Device device) {
        deviceDao.save(device);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"dispatcher", "administrator"})
    public Response updateDevice(Device device) {
        deviceDao.update(device);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"administrator"})
    public Response deleteDevice(Device device) {
        deviceDao.delete(device);
        return Response.status(Response.Status.OK).build();
    }
}
