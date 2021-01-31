package com.example.TestProject.services.exceptions;

public class AccountNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Account not found";
    }
}
