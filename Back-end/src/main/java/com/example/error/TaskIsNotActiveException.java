package com.example.error;

public class TaskIsNotActiveException extends RuntimeException {

    public TaskIsNotActiveException(String message) {
        super(message);
    }

}
