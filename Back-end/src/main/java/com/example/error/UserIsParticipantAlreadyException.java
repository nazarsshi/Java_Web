package com.example.error;

public class UserIsParticipantAlreadyException extends RuntimeException {

    public UserIsParticipantAlreadyException(String message) {
        super(message);
    }

}
