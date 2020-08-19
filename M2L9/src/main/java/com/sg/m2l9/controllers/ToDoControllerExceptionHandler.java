package com.sg.m2l9.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ToDoControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

}
