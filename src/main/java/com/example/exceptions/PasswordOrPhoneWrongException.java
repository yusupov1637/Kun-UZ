package com.example.exceptions;

public class PasswordOrPhoneWrongException extends RuntimeException{
    public PasswordOrPhoneWrongException(String message) {
        super(message);
    }
}
