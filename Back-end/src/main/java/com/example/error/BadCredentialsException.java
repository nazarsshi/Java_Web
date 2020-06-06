package com.example.error;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException(String message){
        super(message);
    }
}
