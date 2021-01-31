package com.example.TestProject.services;

import com.example.TestProject.dto.UserDataDTO;
import com.example.TestProject.entity.Account;
import com.example.TestProject.entity.UserData;
import com.example.TestProject.repozitoties.AccountRepository;
import com.example.TestProject.services.exceptions.AccountNotFoundException;
import com.example.TestProject.services.security.AuthenticationFacade;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service("/profile")
public class UserDataService {
    final AccountRepository accountRepozitory;

    final AuthenticationFacade authenticationFacade;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Modifying
    public ResponseEntity<UserData> updateUserDataForCurrentUser(UserDataDTO userDataDTO) {
        String username = authenticationFacade.getAuthentication().getName();
        Account account = accountRepozitory.getAccountByAccountCredentials_Username(username).orElseThrow(AccountNotFoundException::new);
        UserData userData = UserData.builder()
                .firstname(userDataDTO.getFirstname())
                .secondName(userDataDTO.getSecondName())
                .thirdName(userDataDTO.getThirdName())
                .educationType(userDataDTO.getEducationType())
                .dateOfBirth(userDataDTO.getDateOfBirth())
                .maritalStatus(userDataDTO.getMaritalStatus())
                .account(account)
                .build();
        account.setUserdata(userData);
        accountRepozitory.save(account);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Transactional
    @Modifying
    public ResponseEntity<Account> updateCustomer(UserDataDTO dto) {
        String username = authenticationFacade.getAuthentication().getName();
        Account account = accountRepozitory.getAccountByAccountCredentials_Username(username).orElseThrow(AccountNotFoundException::new);
        //По другому не знаю пока что
        if (dto.getFirstname() != null) {
            account.getUserdata().setFirstname(dto.getFirstname());
        }
        if (dto.getSecondName() != null) {
            account.getUserdata().setSecondName(dto.getSecondName());
        }
        if (dto.getThirdName() != null) {
            account.getUserdata().setThirdName(dto.getThirdName());
        }
        if (dto.getDateOfBirth() != null) {
            account.getUserdata().setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getMaritalStatus() != null) {
            account.getUserdata().setMaritalStatus(dto.getMaritalStatus());
        }
        if (dto.getEducationType() != null) {
            account.getUserdata().setEducationType(dto.getEducationType());
        }
        accountRepozitory.save(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public UserData getUserData() {
        String username = authenticationFacade.getAuthentication().getName();
        return accountRepozitory.getAccountByAccountCredentials_Username(username)
                .orElseThrow(AccountNotFoundException::new)
                .getUserdata();
    }

    public UserDataService(AccountRepository accountRepozitory, AuthenticationFacade authenticationFacade) {
        this.accountRepozitory = accountRepozitory;
        this.authenticationFacade = authenticationFacade;
    }
}
