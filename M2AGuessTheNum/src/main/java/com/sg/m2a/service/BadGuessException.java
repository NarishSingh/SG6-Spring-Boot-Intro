package com.sg.m2a.service;

public class BadGuessException extends Exception {

    public BadGuessException(String message) {
        super(message);
    }

    public BadGuessException(String message, Throwable cause) {
        super(message, cause);
    }

}
