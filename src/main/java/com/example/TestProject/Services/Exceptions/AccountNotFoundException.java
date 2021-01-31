package com.example.TestProject.Services.Exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super("Account not found");
    }

    @Override
    public String getMessage() {
        return "Account not found";
    }
}
