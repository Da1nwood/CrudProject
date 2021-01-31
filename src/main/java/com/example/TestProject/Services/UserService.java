package com.example.TestProject.Services;

import com.example.TestProject.DTO.AuthorizationDTO;
import com.example.TestProject.Entity.Account;
import com.example.TestProject.Entity.AccountCredentials;
import com.example.TestProject.Entity.Enums.Roles;
import com.example.TestProject.Entity.Enums.TypeOfSegment;
import com.example.TestProject.Entity.UserData;
import com.example.TestProject.Repozitories.AccountCredentialsRepozitory;
import com.example.TestProject.Repozitories.AccountRepozitory;
import com.example.TestProject.Services.Security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    final AccountRepozitory accountRepozitory;
    final AccountCredentialsRepozitory accountCredentialsRepozitory;
    private final AuthenticationFacade authenticationFacade;

    public UserService(PasswordEncoder passwordEncoder, AccountRepozitory accountRepozitory, AccountCredentialsRepozitory accountCredentialsRepozitory, AuthenticationFacade authenticationFacade) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepozitory = accountRepozitory;
        this.accountCredentialsRepozitory = accountCredentialsRepozitory;
        this.authenticationFacade = authenticationFacade;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountCredentials user = accountCredentialsRepozitory
                .findAccountCredentialsByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no such user"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Roles role: user.getAuthorities()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return new User(user.getUsername(), user.getPassword(), user.isEnabled()
                ,user.isAccountNonExpired()
                ,user.isCredentialsNonExpired()
                ,user.isAccountNonLocked()
                ,grantedAuthorities);
    }

    @Transactional(readOnly = true)
    Optional<AccountCredentials> userIsExists(String username){
        return accountCredentialsRepozitory.findAccountCredentialsByUsername(username);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Account> addNewUser(AuthorizationDTO authorizationDTO) throws Exception {
        if(userIsExists(authorizationDTO.getUsername()).isPresent())throw new Exception();
        Account account = new Account();
        UserData userData = UserData.builder().account(account).build();
        AccountCredentials accountCredentials = new AccountCredentials();
        accountCredentials.setPassword(passwordEncoder.encode(authorizationDTO.getPassword()));
        accountCredentials.setAuthorities(Collections.singleton(Roles.ROLE_USER));
        accountCredentials.setAccountNonExpired(true);
        accountCredentials.setAccountNonLocked(true);
        accountCredentials.setCredentialsNonExpired(true);
        accountCredentials.setEnabled(true);
        accountCredentials.setAccount(account);
        account.getTypeOfSegments().add(TypeOfSegment.COMMON);
        accountCredentials.setUsername(authorizationDTO.getUsername());
        userData.setAccount(account);
        account.setUserdata(userData);
        account.setAccountCredentials(accountCredentials);
        accountRepozitory.save(account);
        return new ResponseEntity<Account>(account, HttpStatus.CREATED);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Modifying
    public ResponseEntity<String> deleteUserAccount(){
        String username =  authenticationFacade.getAuthentication().getName();
        accountRepozitory.deleteAccountByAccountCredentials_Username(username);
        authenticationFacade.getAuthentication().setAuthenticated(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Account> getCurrentUser() throws AccountNotFoundException {
        String username =  authenticationFacade.getAuthentication().getName();
        Optional<Account> account = accountRepozitory.getAccountByAccountCredentials_Username(username);
        return new ResponseEntity<>(account.orElseThrow(AccountNotFoundException::new),HttpStatus.OK);
    }

}
