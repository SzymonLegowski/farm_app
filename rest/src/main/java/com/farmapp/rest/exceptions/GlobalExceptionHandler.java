package com.farmapp.rest.exceptions;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotFoundException(NotFoundException ex){
        
        ErrorObject errorObject = createErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAssignedEntitiesException.class)
    public ResponseEntity<ErrorObject> HandleNoAssignedEntitiesException(NoAssignedEntitiesException ex)
    {
        ErrorObject errorObject = createErrorObject(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoFieldsException.class)
    public ResponseEntity<ErrorObject> HandleNoFieldsException(NoFieldsException ex)
    {
        ErrorObject errorObject = createErrorObject(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorObject> handleDataIntegrity(DataIntegrityViolationException ex){
        if(ex.getMessage() != null && ex.getMessage().contains("unique_active_sow")) {
            ErrorObject errorObject = createErrorObject(HttpStatus.CONFLICT.value(), "There already exists active sow with given number.");
            return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
        }
        if(ex.getMessage() != null && ex.getMessage().contains("unique_event_same_day")) {
            ErrorObject errorObject = createErrorObject(HttpStatus.CONFLICT.value(), "There already exists event of this type on given date.");
            return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
        }
        ErrorObject errorObject = createErrorObject(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }



    private ErrorObject createErrorObject(Integer statusCode, String message){
        ErrorObject errorObject = new ErrorObject();
        errorObject. setStatusCode(statusCode);
        errorObject.setMessage(message);
        errorObject.setTimestamp(new Date());
        return errorObject;
    }
}
