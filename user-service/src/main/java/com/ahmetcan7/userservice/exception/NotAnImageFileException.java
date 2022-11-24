package com.ahmetcan7.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAnImageFileException extends RuntimeException {
    public NotAnImageFileException(String message) {
        super(message);
    }
}
