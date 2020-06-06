package com.example.error;

public class UserIsNotCreatorException extends RuntimeException {

    public UserIsNotCreatorException(String message) {
        super(message);
    }

}