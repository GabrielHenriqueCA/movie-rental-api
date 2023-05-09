package com.api.movierental.exceptions;

public class RentalNotFoundException extends Exception{
    public RentalNotFoundException(String message) {
        super(message);
    }
}
