package com.gym_app.gym_app.exceptions;

import com.gym_app.gym_app.dto.responses.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(String error, String message, HttpStatus status) {

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .error(error)
                .message(message)
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handlerBadRequestException(BadRequestException badRequestException) {

//        Map<String,Object> errors = new HashMap<>();
//
//        errors.put("error","Bad request");
//        errors.put("message",badRequestException.getMessage());
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

        return buildErrorResponse("Bad Request",badRequestException.getMessage(),HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {

//        Map<String,Object> errors = new HashMap<>();
//
//        errors.put("error","Resource not found");
//        errors.put("message",resourceNotFoundException.getMessage());
//
//        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

        return buildErrorResponse("Resource not found", resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

//        Map<String,Object> errors = new HashMap<>();
//
//        ex.getBindingResult().getFieldErrors().forEach(error->
//                errors.put(error.getField(),error.getDefaultMessage()));
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

        Map<String,Object> detailsValidation = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage())
                );

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .error("Validation failed")
                .message("One or more fields failed validation")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .details(detailsValidation)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
