package com.example.TestProject.RestController;

import com.example.TestProject.Entity.Account;
import com.example.TestProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/account")
public class AccountController {
    final UserService userService;

    @GetMapping("/delete")
    public ResponseEntity<String> deleteCurrentSessionUser(){
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
