package com.tscode.controller;

import com.tscode.model.dto.EmployeeDto;
import com.tscode.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/employees")
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @GET
    @Path("/fetch/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void fetchAndSaveEmployee(@PathParam("employeeId") Long employeeId) {
        employeeService.fetchAndSaveEmployee(employeeId);
    }

    @GET
    @Path("/save-list")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveEmployeeList() {
        employeeService.saveEmployeeList();
    }
}
