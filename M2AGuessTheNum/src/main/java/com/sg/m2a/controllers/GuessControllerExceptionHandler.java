package com.sg.m2a.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GuessControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Deal with HTTP responses for exceptions
     *
     * @param ex      {SQLIntegrityConstraintViolationException} any SQL and DB
     *                related exception from API run
     * @param request {WebRequest}
     * @return {ResponseEntity}
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        Error e = new Error();
        e.setMessage(ex.getMessage());

        return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
    }
    
}
