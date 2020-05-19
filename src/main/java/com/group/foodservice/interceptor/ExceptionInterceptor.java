package com.group.foodservice.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.group.foodservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@ControllerAdvice
@ResponseBody
public class ExceptionInterceptor {

    /**
     * 用于处理自定义的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BadRequestException.class,
            ServerInternalException.class,
            AccessDeniedException.class
    })
    public String badRequestExceptionHandler(HttpClientErrorException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusCode", e.getStatusCode().value());
        jsonObject.put("status", false);
        jsonObject.put("msg", "请正确填写信息");
        return jsonObject.toJSONString();
    }

    /**
     * 用于处理valid注解的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class
    })
    public String validationExceptionHandler(MethodArgumentNotValidException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusCode", HttpStatus.BAD_REQUEST.value());
        jsonObject.put("status", false);
        jsonObject.put("msg", "请正确填写信息");
        return jsonObject.toJSONString();
    }

    /**
     * 用于处理valid注解的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {
            ConstraintViolationException.class
    })
    public String validationExceptionHandler2(Exception e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusCode", HttpStatus.BAD_REQUEST.value());
        jsonObject.put("status", false);
        jsonObject.put("msg", "请正确填写信息");
        return jsonObject.toJSONString();
    }
}
