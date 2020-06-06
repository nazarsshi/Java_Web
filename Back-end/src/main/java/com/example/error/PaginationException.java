package com.example.error;

public class PaginationException extends RuntimeException {

    public PaginationException(String message){
        super(message);
    }

}