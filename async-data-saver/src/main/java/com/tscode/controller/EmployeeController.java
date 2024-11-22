package com.tscode.controller;

import com.tscode.common.constant.MessageConstants;
import com.tscode.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/employees")
@Slf4j
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @GET
    @Path("/fetch/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAndSaveEmployee(@PathParam("employeeId") Long employeeId) {
        try {
            log.info("Fetching and saving employee with ID: {}", employeeId);
            employeeService.fetchAndSaveEmployee(employeeId);
            log.info("Employee with ID: {} fetched and saved successfully", employeeId);
            return Response.status(Response.Status.OK).entity("Employee fetched and saved successfully").build();
        } catch (Exception e) {
            log.error("Error occurred while fetching and saving employee with ID: {}", employeeId, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch and save employee").build();
        }
    }

    @GET
    @Path("/save-list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveEmployeeList() {
        try {
            log.info("Starting to save employee list");
            employeeService.saveEmployeeList();
            log.info("Employee list saved successfully");
            return Response.status(Response.Status.OK).entity("Employee list saved successfully").build();
        } catch (Exception e) {
            log.error("Error occurred while saving the employee list", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to save employee list").build();
        }
    }
}
