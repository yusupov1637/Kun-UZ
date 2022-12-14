package com.example.exceptions;

public class PhonoAlreadyExistsException extends RuntimeException{
    public PhonoAlreadyExistsException(String message) {
        super(message);
    }
}
