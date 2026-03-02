package com.farmapp.rest.exceptions;

public class NoAssignedEntitiesException extends RuntimeException{
    private static final long serialVersionUID = 2;

    public NoAssignedEntitiesException(String message){
        super(message);
    }
}
