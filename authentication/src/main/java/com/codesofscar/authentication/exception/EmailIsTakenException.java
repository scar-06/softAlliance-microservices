package com.codesofscar.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmailIsTakenException extends RuntimeException{
    public EmailIsTakenException(String message) {
        super(message);
    }
}
