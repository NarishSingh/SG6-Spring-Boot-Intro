package com.sg.m2a.service;

public class GameCompleteException extends Exception {

    public GameCompleteException(String message) {
        super(message);
    }

    public GameCompleteException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
