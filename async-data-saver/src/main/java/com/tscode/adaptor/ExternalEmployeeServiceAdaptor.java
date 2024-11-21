package com.tscode.adaptor;

import com.tscode.model.dto.EmployeeDto;
import com.tscode.model.dto.EmployeeLIstDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "api-ext")
public interface ExternalEmployeeServiceAdaptor {


    @GET
    @Path("/api-fe/get-employee/{employeeId}")
    @Produces("application/json")
    EmployeeDto getEmployee(@PathParam("employeeId") Long employeeId);

    @GET
    @Path("/api-ext/list-employee")
    @Produces("application/json")
    EmployeeLIstDto postEmployeeList();

/*    public static class Response {
        public String message;
    }*/
}
