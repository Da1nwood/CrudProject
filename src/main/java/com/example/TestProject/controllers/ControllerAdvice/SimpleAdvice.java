package com.example.TestProject.controllers.ControllerAdvice;

import com.example.TestProject.services.exceptions.AccountNotFoundException;
import com.example.TestProject.services.exceptions.FamilyNotFoundExceptions;
import com.example.TestProject.services.exceptions.IllegalCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SimpleAdvice
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {AccountNotFoundException.class, FamilyNotFoundExceptions.class})
    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler(value
            = {IllegalCredentialsException.class, UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleSecurityException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}