package com.example.TestProject.services.exceptions;

public class FamilyNotFoundExceptions extends RuntimeException {
    @Override
    public String getMessage() {
        return "Family not found";
    }
}
