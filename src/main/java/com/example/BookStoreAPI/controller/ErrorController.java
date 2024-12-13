package com.example.BookStoreAPI.controller;

import com.example.BookStoreAPI.error.CustomErrorResponse;
import com.example.BookStoreAPI.exception.AlreadyExistsException;
import com.example.BookStoreAPI.exception.DeleteException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> entityNotFoundException(EntityNotFoundException ex){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                HttpStatus.NOT_FOUND.value(), ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customErrorResponse);
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<CustomErrorResponse> deleteException(DeleteException ex){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(), ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponse);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> alreadyExistException(AlreadyExistsException ex){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(), ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
        //Creating a hashmap for errors.
        Map<String, String> errors = new HashMap<>();

        //Iterating through every error and save them to the hashmap.
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> globalErrorHandler(Exception ex){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customErrorResponse);
    }
}
