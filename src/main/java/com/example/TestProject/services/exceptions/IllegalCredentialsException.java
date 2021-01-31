package com.example.TestProject.services.exceptions;

public class IllegalCredentialsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Illegal credentials";
    }
}
