package com.web.resources;

import com.model.Employee;
import com.model.enums.SortingOrder;
import com.service.database.DatabaseService;
import com.service.database.dao.EmployeeDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employees")
public class EmployeeResource {

    private EmployeeDao employeeDao;

    public EmployeeResource() {
        DatabaseService databaseService = DatabaseService.getInstance();
        this.employeeDao = databaseService.getEmployeeDao();
    }

    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@QueryParam("phoneNumber") String phoneNumber) {
        return employeeDao.get(phoneNumber);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeeDao.getAll("lastName", SortingOrder.ASC);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) {
        employeeDao.save(employee);
        return Response.ok().build();
    }

    @PUT
    public Response updateEmployee(Employee employee) {
        employeeDao.update(employee);
        return Response.ok().build();
    }

    @DELETE
    public Response deleteEmployee(Employee employee) {
        employeeDao.delete(employee);
        return Response.ok().build();
    }
}