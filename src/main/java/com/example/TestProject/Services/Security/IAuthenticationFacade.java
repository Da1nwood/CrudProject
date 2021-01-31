package com.example.TestProject.Services.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


public interface IAuthenticationFacade {
    Authentication getAuthentication();
}