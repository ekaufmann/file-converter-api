package com.ekaufmann.file_converter_api.controller.advice;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = { MissingServletRequestPartException.class, BadRequestException.class })
    protected ResponseEntity<Object> handleBadRequest(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }
}
