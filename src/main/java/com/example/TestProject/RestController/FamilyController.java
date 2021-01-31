package com.example.TestProject.RestController;

import com.example.TestProject.DTO.FamilyDTO;
import com.example.TestProject.Entity.Account;
import com.example.TestProject.Services.Exceptions.AccountNotFoundException;
import com.example.TestProject.Services.Exceptions.FamilyNotFoundExceptions;
import com.example.TestProject.Services.FamilyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/family")
public class FamilyController {
    final FamilyService familyService;

    @PostMapping("/create")
    ResponseEntity<String> createFamily(){
        return familyService.createFamily();
    }

    @PutMapping("/add")
    ResponseEntity<Void> addAccountToFamily(@RequestBody FamilyDTO familyDTO) throws AccountNotFoundException, FamilyNotFoundExceptions {
        return familyService.addUsersToFamily(familyDTO);
    }
    @GetMapping("/users")
    ResponseEntity<List<Account>> getFamilyAccountIds() throws AccountNotFoundException, FamilyNotFoundExceptions {
        return familyService.getAccountsByFamily();
    }
    @DeleteMapping("/users")
    ResponseEntity<List<Long>> removeAccountFromFamily() throws AccountNotFoundException , FamilyNotFoundExceptions {
        return familyService.removeAccountFromFamily();
    }

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }
}
