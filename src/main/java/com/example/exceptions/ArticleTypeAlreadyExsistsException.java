package com.example.exceptions;

public class ArticleTypeAlreadyExsistsException extends RuntimeException{
    public ArticleTypeAlreadyExsistsException(String message) {
        super(message);
    }
}
