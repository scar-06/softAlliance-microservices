package com.codesofscar.employee_mgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotEligibleException extends RuntimeException {
    public UserNotEligibleException(String message) {}
}
