package com.example.controller;

import com.example.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({
        PhonoAlreadyExistsException.class,
            AppBadRequestException.class,
            ItemNotFoundException.class,
            RegionAlreadyExsistException.class,
            TagAlreadyExsistException.class,
            CategoryAlreadyExsistException.class,
            PasswordOrPhoneWrongException.class,
            ArticleTypeAlreadyExsistsException.class,
            ItemAlreadyExistsException.class,
            AppForbiddenException.class,
    })

    public ResponseEntity<?> handle(RuntimeException runtimeException) {
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }

    @ExceptionHandler({TokenNotValidException.class})
    public ResponseEntity<?> handleForbidden(TokenNotValidException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
