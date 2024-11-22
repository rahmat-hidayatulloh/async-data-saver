package com.tscode.exception;

import com.tscode.common.constant.MessageConstants;
import com.tscode.model.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(MessageConstants.BUSINESS_ERROR_CODE, e.getMessage()))
                .build();
    }
}
