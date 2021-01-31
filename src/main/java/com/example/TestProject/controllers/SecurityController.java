package com.example.TestProject.controllers;

import com.example.TestProject.dto.AuthorizationDTO;
import com.example.TestProject.entity.Account;
import com.example.TestProject.services.exceptions.IllegalCredentialsException;
import com.example.TestProject.services.security.SecurityService;
import com.example.TestProject.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SecurityController {
    final SecurityService securityService;

    final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthorizationDTO authorizationDTO) throws IllegalCredentialsException {
        return securityService.authorization(authorizationDTO);
    }

    @PostMapping("/registration")
    public ResponseEntity<Account> registration(@RequestBody AuthorizationDTO authorizationDTO) throws Exception {
        return userService.addNewUser(authorizationDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return securityService.logout();
    }

    public SecurityController(SecurityService securityService, @Qualifier("userService") UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }
}
