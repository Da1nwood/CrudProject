package com.example.TestProject.controllers;

import com.example.TestProject.dto.FamilyDTO;
import com.example.TestProject.entity.Account;
import com.example.TestProject.services.exceptions.AccountNotFoundException;
import com.example.TestProject.services.exceptions.FamilyNotFoundExceptions;
import com.example.TestProject.services.FamilyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/family")
public class FamilyController {
    final FamilyService familyService;

    @PostMapping
    ResponseEntity<String> createFamily() {
        return familyService.createFamily();
    }

    @PutMapping
    ResponseEntity<Void> addAccountToFamily(@RequestBody FamilyDTO familyDTO) throws AccountNotFoundException, FamilyNotFoundExceptions {
        return familyService.addUsersToFamily(familyDTO);
    }

    @GetMapping
    ResponseEntity<List<Account>> getFamilyAccountIds() throws AccountNotFoundException, FamilyNotFoundExceptions {
        return familyService.getAccountsByFamily();
    }

    @DeleteMapping
    ResponseEntity<List<Long>> removeAccountFromFamily() throws AccountNotFoundException, FamilyNotFoundExceptions {
        return familyService.removeAccountFromFamily();
    }

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }
}
