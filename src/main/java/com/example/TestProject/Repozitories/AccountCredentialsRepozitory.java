package com.example.TestProject.Repozitories;

import com.example.TestProject.Entity.Account;
import com.example.TestProject.Entity.AccountCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCredentialsRepozitory extends JpaRepository<AccountCredentials, Long> {
    //@Query("SELECT a from AccountCredentials a where a.username  = :username")
    Optional<AccountCredentials> findAccountCredentialsByUsername(String username);
}
