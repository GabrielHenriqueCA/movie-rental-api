package com.api.movierental.exceptions;

public class TokenInvalidException extends  RuntimeException {

    public TokenInvalidException(String message) {
        super(message);
    }
}
