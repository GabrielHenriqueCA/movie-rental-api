package com.api.movierental.exceptions;

public class ReturnDateException extends RuntimeException {
    public ReturnDateException(String message) {
        super(message);
    }
}
