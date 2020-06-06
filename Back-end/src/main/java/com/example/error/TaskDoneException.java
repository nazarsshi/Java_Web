package com.example.error;

public class TaskDoneException extends RuntimeException {

    public TaskDoneException(String message) {
        super(message);
    }

}
