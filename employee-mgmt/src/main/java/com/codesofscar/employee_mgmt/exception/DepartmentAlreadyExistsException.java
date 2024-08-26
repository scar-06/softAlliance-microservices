package com.codesofscar.employee_mgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DepartmentAlreadyExistsException extends RuntimeException{

    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
