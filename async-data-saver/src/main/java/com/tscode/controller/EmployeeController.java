package com.tscode.controller;

import com.tscode.model.dto.EmployeeDto;
import com.tscode.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
            log.info("Received request to fetch and save employee with ID: {}", employeeId);
            EmployeeDto result = employeeService.fetchAndSaveEmployee(employeeId).join();
            log.info("Employee with ID: {} processed successfully", employeeId);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            log.error("Error processing employee with ID {}: {}", employeeId, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch and save employee: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/save-list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveEmployeeList() {
        try {
            log.info("Received request to save employee list");
            List<EmployeeDto> result = employeeService.saveEmployeeList().join();
            log.info("Employee list processed successfully");
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            log.error("Error saving employee list: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to save employee list: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/find/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEmployeeById(@PathParam("employeeId") Long employeeId) {
        try {
            log.info("Received request to find employee with ID: {}", employeeId);
            return Response.status(Response.Status.OK)
                    .entity(employeeService.findEmployeeById(employeeId)).build();
        } catch (Exception e) {
            log.error("Error finding employee with ID {}: {}", employeeId, e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found: " + e.getMessage()).build();
        }
    }
}
