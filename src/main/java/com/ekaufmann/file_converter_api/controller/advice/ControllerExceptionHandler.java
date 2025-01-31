package com.ekaufmann.file_converter_api.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { MissingServletRequestPartException.class })
    protected ResponseEntity<Object> handleBadRequest(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
