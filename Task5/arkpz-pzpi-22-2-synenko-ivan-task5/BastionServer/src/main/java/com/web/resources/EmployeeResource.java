package com.web.resources;

import com.model.Employee;
import com.model.enums.SortingOrder;
import com.service.database.DatabaseService;
import com.service.database.dao.EmployeeDao;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/***************************************

 Resource used to access employees collection
 in database and perform CRUD operations on it.

 **************************************/

@Path("/employees")
public class EmployeeResource {

    private EmployeeDao employeeDao
            = DatabaseService
            .getInstance()
            .getEmployeeDao();



    @GET
    @Path("/view")
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@QueryParam("phoneNumber") String phoneNumber) {
        return employeeDao.get(phoneNumber);
    }

    @GET
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeeDao.getAll("lastName", SortingOrder.ASC);
    }

    @GET
    @Path("/on-shift")
    @RolesAllowed({"dispatcher", "administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllOnShift() {
        return employeeDao.getAllOnShift("lastName", SortingOrder.ASC);
    }

    @POST
    @RolesAllowed({"dispatcher", "administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) {
        employeeDao.save(employee);
        return Response.ok().build();
    }

    @PUT
    @RolesAllowed({"dispatcher", "administrator"})
    public Response updateEmployee(Employee employee) {
        employeeDao.update(employee);
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed({"dispatcher", "administrator"})
    public Response deleteEmployee(Employee employee) {
        employeeDao.delete(employee);
        return Response.ok().build();
    }
}