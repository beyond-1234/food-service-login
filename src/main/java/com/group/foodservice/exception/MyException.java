package com.group.foodservice.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {

    protected HttpStatus statusCode;
    protected String statusText;

    public MyException(HttpStatus statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }
}
