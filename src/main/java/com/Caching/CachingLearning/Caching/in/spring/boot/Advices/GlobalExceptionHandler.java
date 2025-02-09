package com.Caching.CachingLearning.Caching.in.spring.boot.Advices;

import com.Caching.CachingLearning.Caching.in.spring.boot.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFountException(ResourceNotFoundException exception) {
        ApiError apiError = new ApiError(
                exception.getLocalizedMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException exception) {
        ApiError apiError = new ApiError(
                exception.getLocalizedMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
