package com.explore.africa.exceptions;

import com.explore.africa.dto.APIErr;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIErr> handleNotFound(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new APIErr("User not found",
                        ex.getMessage())
        );
    }
}
