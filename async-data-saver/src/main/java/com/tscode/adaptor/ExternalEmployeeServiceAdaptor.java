package com.tscode.adaptor;

import com.tscode.common.Interceptor.Logging;
import com.tscode.common.Interceptor.LoggingInterceptor;
import com.tscode.model.dto.EmployeeDto;
import com.tscode.model.dto.EmployeeLIstDto;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "api-ext")
@Interceptors(LoggingInterceptor.class)
@Logging
public interface ExternalEmployeeServiceAdaptor {


    @GET
    @Path("/api-ext/get-employee/{employeeId}")
    @Produces("application/json")
    EmployeeDto getSourceEmployee(@PathParam("employeeId") Long employeeId);

    @GET
    @Path("/api-ext/list-employee")
    @Produces("application/json")
    EmployeeLIstDto getSourceEmployeeList();

}
