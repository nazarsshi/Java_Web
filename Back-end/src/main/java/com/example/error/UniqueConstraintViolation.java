package com.example.error;

public class UniqueConstraintViolation extends RuntimeException {

    public UniqueConstraintViolation(String message) {
        super(message);
    }

}