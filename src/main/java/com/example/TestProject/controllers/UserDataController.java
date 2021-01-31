package com.example.TestProject.controllers;

import com.example.TestProject.dto.UserDataDTO;
import com.example.TestProject.entity.Account;
import com.example.TestProject.entity.UserData;
import com.example.TestProject.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserDataController {
    @Autowired
    UserDataService userDataService;

    @PutMapping
    public ResponseEntity<Account> updateUserData(@RequestBody UserDataDTO userDataDTO) {
        return userDataService.updateCustomer(userDataDTO);
    }

    @GetMapping
    public ResponseEntity<UserData> getUserData() {
        return new ResponseEntity<>(userDataService.getUserData(), HttpStatus.OK);
    }
}
