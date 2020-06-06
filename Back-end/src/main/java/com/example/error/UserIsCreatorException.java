package com.example.error;

public class UserIsCreatorException extends RuntimeException {

    public UserIsCreatorException(String message) {
        super(message);
    }

}
