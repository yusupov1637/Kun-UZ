package com.example.exceptions;

public class CategoryAlreadyExsistException extends RuntimeException {
    public CategoryAlreadyExsistException(String message) {
        super(message);
    }
}
