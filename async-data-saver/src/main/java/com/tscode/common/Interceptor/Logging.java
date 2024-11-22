package com.tscode.common.Interceptor;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD}) // Bisa diterapkan pada kelas atau metode
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {
}