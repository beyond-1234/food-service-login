package com.group.foodservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "请求参数有误")
public class BadRequestException extends MyException {

    public BadRequestException(String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
    }

}
