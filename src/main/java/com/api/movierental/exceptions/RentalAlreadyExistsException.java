package com.api.movierental.exceptions;

public class RentalAlreadyExistsException extends RuntimeException {
    public RentalAlreadyExistsException(String message) {
        super(message);
    }
}
