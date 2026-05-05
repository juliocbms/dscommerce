package com.devjulio.dscommerce.controllers.handlers;

import com.devjulio.dscommerce.DTO.CustomError;
import com.devjulio.dscommerce.services.exceptions.DatabaseException;
import com.devjulio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExcpetionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFoundException (ResourceNotFoundException e , HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(),e.getMessage(), request.getRequestURI());
        return  ResponseEntity.status(status).body(error);
    }


    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseException (DatabaseException e , HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(),e.getMessage(), request.getRequestURI());
        return  ResponseEntity.status(status).body(error);
    }
}
