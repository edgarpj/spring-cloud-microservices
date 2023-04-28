package com.example.productsservice.controllers;

import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NoFallbackAvailableException.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception e) {
        Map<String, String> message = Collections.singletonMap("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
