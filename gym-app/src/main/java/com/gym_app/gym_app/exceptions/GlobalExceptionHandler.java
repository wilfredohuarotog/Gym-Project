package com.gym_app.gym_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException badRequestException){

        Map<String,Object> errors = new HashMap<>();

        errors.put("error","Bad request");
        errors.put("message",badRequestException.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){

        Map<String,Object> errors = new HashMap<>();

        errors.put("error","Resource not found");
        errors.put("message",resourceNotFoundException.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }


}
