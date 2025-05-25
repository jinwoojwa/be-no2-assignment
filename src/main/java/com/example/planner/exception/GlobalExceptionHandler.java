package com.example.planner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleScheduleNotFound(ScheduleNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Not Found", "message", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequest(InvalidRequestException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Bad Request", "message", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Map<String, String>> handlePasswordMismatch(PasswordMismatchException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Unauthorized", "message", ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
        return new ResponseEntity<>(
                Map.of("error", "Internal Server Error", "message", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
