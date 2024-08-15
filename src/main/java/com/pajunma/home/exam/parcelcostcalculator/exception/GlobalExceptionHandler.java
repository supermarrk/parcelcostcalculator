package com.pajunma.home.exam.parcelcostcalculator.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            RuntimeException.class
    })
    public ResponseEntity<Object> httpConnectTimeoutException(NoSuchElementException e, WebRequest request) {
        String errorBodyResponse = "RuntimeException encountered";
        return handleExceptionInternal(e, errorBodyResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
