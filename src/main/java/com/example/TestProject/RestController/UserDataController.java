package com.example.TestProject.RestController;

import com.example.TestProject.DTO.UserDataDTO;
import com.example.TestProject.Entity.Account;
import com.example.TestProject.Entity.UserData;
import com.example.TestProject.Services.UserDataService;
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
    public ResponseEntity<Account> updateUserData(@RequestBody UserDataDTO userDataDTO){
        return userDataService.updateCustomer(userDataDTO);
    }
    @GetMapping
    public ResponseEntity<UserData> getUserData(){
        return new ResponseEntity<>(userDataService.getUserData(), HttpStatus.OK);
    }
}
