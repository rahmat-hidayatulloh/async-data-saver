package com.tscode.common.Interceptor;


import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Interceptor
@Logging
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethod(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        Object[] params = context.getParameters();

        log.info("Invoking method: {} with parameters: {}", methodName, params);
        try {
            Object result = context.proceed();
            log.info("Method {} executed successfully. Result: {}", methodName, result);
            return result;
        } catch (Exception e) {
            log.error("Error occurred while executing method: {}", methodName, e);
            throw e;
        }
    }
}