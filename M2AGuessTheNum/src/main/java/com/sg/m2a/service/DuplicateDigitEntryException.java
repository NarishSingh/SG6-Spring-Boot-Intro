package com.sg.m2a.service;

public class DuplicateDigitEntryException extends Exception {

    public DuplicateDigitEntryException(String message) {
        super(message);
    }

    public DuplicateDigitEntryException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
