package com.group.foodservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "服务器内部错误")
public class ServerInternalException extends MyException {

    public ServerInternalException(String statusText) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, statusText);
    }
}
