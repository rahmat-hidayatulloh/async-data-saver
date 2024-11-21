package com.tscode.controller;

import com.tscode.model.dto.EmployeeDto;
import com.tscode.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employees")
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @GET
    @Path("/fetch/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAndSaveEmployee(@PathParam("employeeId") Long employeeId) {
        employeeService.fetchAndSaveEmployee(employeeId);
        return Response.status(Response.Status.OK).entity("Employee fetched and saved successfully").build();
    }

    @GET
    @Path("/save-list")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveEmployeeList() {
        employeeService.saveEmployeeList();
        return Response.status(Response.Status.OK).entity("Employee list saved successfully").build();
    }

}
