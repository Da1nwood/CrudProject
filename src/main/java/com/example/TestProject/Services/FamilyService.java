package com.example.TestProject.Services;

import com.example.TestProject.DTO.FamilyDTO;
import com.example.TestProject.Entity.Account;
import com.example.TestProject.Entity.Family;
import com.example.TestProject.Repozitories.AccountRepozitory;
import com.example.TestProject.Repozitories.FamilyRepoz;
import com.example.TestProject.Services.Exceptions.AccountNotFoundException;
import com.example.TestProject.Services.Exceptions.FamilyNotFoundExceptions;
import com.example.TestProject.Services.Security.AuthenticationFacade;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FamilyService {
    final FamilyRepoz familyRepoz;
    final AccountRepozitory accountRepozitory;
    final AuthenticationFacade authenticationFacade;

    public FamilyService(FamilyRepoz familyRepoz, AccountRepozitory accountRepozitory, AuthenticationFacade authenticationFacade) {
        this.familyRepoz = familyRepoz;
        this.accountRepozitory = accountRepozitory;
        this.authenticationFacade = authenticationFacade;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> createFamily() {
        String username =  authenticationFacade.getAuthentication().getName();
        Family family = Optional.ofNullable(accountRepozitory.getAccountByAccountCredentials_Username(username)
                .orElseThrow(AccountNotFoundException::new).getFamily())
                .orElseGet(Family::new);
        return new ResponseEntity<>(String.valueOf(familyRepoz.save(family)), HttpStatus.CREATED);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Void> addUsersToFamily(FamilyDTO familyDTO) throws FamilyNotFoundExceptions, AccountNotFoundException {
        Family family = familyRepoz.findFamilyById(familyDTO.getFamilyId())
                .orElseThrow(FamilyNotFoundExceptions::new);
        familyDTO.getAccountUsernames()
                .stream()
                .map(names -> accountRepozitory.getAccountByAccountCredentials_Username(names))
                .flatMap(x -> Stream.of(x.orElseThrow(AccountNotFoundException::new)))
                .forEach(family::addAccountToFamily);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<String> getFamilyIdForUser() throws FamilyNotFoundExceptions, AccountNotFoundException {
        String username =  authenticationFacade.getAuthentication().getName();
        Account account = accountRepozitory.getAccountByAccountCredentials_Username(username).orElseThrow(AccountNotFoundException::new);
        Long familyId = Optional.of(account.getFamily().getId()).orElseThrow(FamilyNotFoundExceptions::new);
        return new ResponseEntity<>(String.valueOf(familyId),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<List<Account>> getAccountsByFamily() throws FamilyNotFoundExceptions, AccountNotFoundException {
        String username =  authenticationFacade.getAuthentication().getName();
        List<Account> accountsId = Optional.ofNullable(accountRepozitory.getAccountByAccountCredentials_Username(username)
                .orElseThrow(AccountNotFoundException::new)
                .getFamily())
                .orElseThrow(FamilyNotFoundExceptions::new)
                .getAccounts();
        return new ResponseEntity<>(accountsId,HttpStatus.OK);
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Modifying
    public ResponseEntity<List<Long>> removeAccountFromFamily() throws FamilyNotFoundExceptions, AccountNotFoundException {
        String username =  authenticationFacade.getAuthentication().getName();
        Account account = accountRepozitory.getAccountByAccountCredentials_Username(username)
                                                     .orElseThrow(AccountNotFoundException::new);
       Optional.of(account
                .getFamily())
                .orElseThrow(FamilyNotFoundExceptions::new)
                .removeAccountFromFamily(account);
       accountRepozitory.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
