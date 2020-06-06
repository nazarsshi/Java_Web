package com.example.error;

public class EntityNotFountException extends RuntimeException {

    public EntityNotFountException(String message) {
        super(message);
    }

}
