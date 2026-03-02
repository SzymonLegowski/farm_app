package com.farmapp.rest.exceptions;

public class NoFieldsException extends RuntimeException{
    private static final long serialVersionUID = 3;

    public NoFieldsException(String message) {
        super(message);
    }
}
