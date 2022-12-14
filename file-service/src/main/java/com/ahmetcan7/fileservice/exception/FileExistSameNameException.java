package com.ahmetcan7.fileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileExistSameNameException extends RuntimeException{
    public FileExistSameNameException(String message){
        super(message);
    }
}
