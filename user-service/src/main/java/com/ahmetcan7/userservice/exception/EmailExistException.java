package com.ahmetcan7.userservice.exception;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
