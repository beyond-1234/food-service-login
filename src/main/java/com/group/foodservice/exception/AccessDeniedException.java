package com.group.foodservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

//@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "该用户权限不够")
public class AccessDeniedException extends MyException {


    public AccessDeniedException(String statusText) {
        super(HttpStatus.FORBIDDEN, statusText);
    }
}
