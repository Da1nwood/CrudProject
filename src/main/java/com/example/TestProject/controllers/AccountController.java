package com.example.TestProject.controllers;

import com.example.TestProject.entity.Account;
import com.example.TestProject.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/account")
public class AccountController {
    final UserService userService;

    @GetMapping("/delete")
    public ResponseEntity<String> deleteCurrentSessionUser() {
        return userService.deleteUserAccount();
    }

    @GetMapping("/getCurrent")
    public ResponseEntity<Account> getCurrentAccount() throws AccountNotFoundException {
        return userService.getCurrentUser();
    }

    public AccountController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }
}
