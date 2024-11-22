package com.tscode.exception;

import com.tscode.common.constant.MessageConstants;
import com.tscode.model.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Slf4j
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {

        log.error(MessageConstants.GLOBAL_EXCEPTION_ERROR, e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(MessageConstants.INTERNAL_SERVER_ERROR_CODE, e.getMessage()))
                .build();
    }
}
