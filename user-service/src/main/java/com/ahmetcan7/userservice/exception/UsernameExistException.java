package com.ahmetcan7.userservice.exception;

public class UsernameExistException extends Exception {
    public UsernameExistException(String message) {
        super(message);
    }
}
