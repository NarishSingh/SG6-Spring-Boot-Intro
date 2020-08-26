package com.sg.m2a.controllers;

import com.sg.m2a.service.DuplicateDigitEntryException;
import com.sg.m2a.service.NotFoundException;
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
     * Deal with HTTP responses for SQL and db related exceptions
     *
     * @param ex      {SQLIntegrityConstraintViolationException} any exception
     *                throw from API run
     * @param request {WebRequest}
     * @return {ResponseEntity} an http response with the exception throw
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(SQLIntegrityConstraintViolationException ex,
            WebRequest request) {
        Error e = new Error();
        e.setMessage(ex.getMessage());

        return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Deal with HTTP responses for faulty guess entry exceptions
     *
     * @param ex      {DuplicateDigitEntryException} if any digit is repeated
     *                within a consumer guess in a round
     * @param request {WebRequest}
     * @return {ResponseEntity} an http response with the exception throw
     */
    @ExceptionHandler(DuplicateDigitEntryException.class)
    public final ResponseEntity<Error> handleDuplicateEntryException(DuplicateDigitEntryException ex,
            WebRequest request) {
        Error e = new Error();
        e.setMessage(ex.getMessage());

        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * Deal with HTTP responses for non-existent games or rounds
     *
     * @param ex      {NotFoundException} if cannot retrieve a game or round
     * @param request {WebRequest}
     * @return {ResponseEntity} an http response with the exception throw
     */
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Error> handleNotFoundException(NotFoundException ex,
            WebRequest request) {
        Error e = new Error();
        e.setMessage(ex.getMessage());

        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }
    
}
