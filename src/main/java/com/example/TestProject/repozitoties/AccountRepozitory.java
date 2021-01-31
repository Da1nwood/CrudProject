package com.example.TestProject.repozitoties;

import com.example.TestProject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepozitory extends JpaRepository<Account, Long> {
    //@Query("select a from Account a where a.accountCredentials.username = :username")
    Optional<Account> getAccountByAccountCredentials_Username(String username);

    Integer deleteAccountByAccountCredentials_Username(String username);
}
