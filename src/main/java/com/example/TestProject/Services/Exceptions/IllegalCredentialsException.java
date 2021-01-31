package com.example.TestProject.Services.Exceptions;

public class IllegalCredentialsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Illegal credentials";
    }
}
