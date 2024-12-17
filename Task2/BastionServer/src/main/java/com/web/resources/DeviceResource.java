package com.web.resources;

import com.model.Device;
import com.model.enums.SortingOrder;
import com.service.database.DatabaseService;
import com.service.database.dao.DeviceDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/devices")
public class DeviceResource {
    private DeviceDao deviceDao;

    public DeviceResource() {
        DatabaseService databaseService = DatabaseService.getInstance();
        this.deviceDao = databaseService.getDeviceDao();
    }

    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@QueryParam("name") String name) {
        return deviceDao.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getAllDevices() {
        return deviceDao.getAll(SortingOrder.ASC);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDevice(Device device) {
        deviceDao.save(device);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDevice(Device device) {
        deviceDao.update(device);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteDevice(Device device) {
        deviceDao.delete(device);
        return Response.status(Response.Status.OK).build();
    }
}
