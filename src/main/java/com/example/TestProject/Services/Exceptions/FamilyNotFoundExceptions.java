package com.example.TestProject.Services.Exceptions;

import java.io.UncheckedIOException;
import java.util.function.Supplier;

public class FamilyNotFoundExceptions extends RuntimeException {
    @Override
    public String getMessage() {
        return "Family not found";
    }
}
