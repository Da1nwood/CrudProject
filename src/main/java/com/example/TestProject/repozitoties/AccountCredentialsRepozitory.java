package com.example.TestProject.repozitoties;

import com.example.TestProject.entity.AccountCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCredentialsRepozitory extends JpaRepository<AccountCredentials, Long> {
    //@Query("SELECT a from AccountCredentials a where a.username  = :username")
    Optional<AccountCredentials> findAccountCredentialsByUsername(String username);
}
