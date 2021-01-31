package com.example.TestProject.services.security;

import com.example.TestProject.dto.AuthorizationDTO;
import com.example.TestProject.services.exceptions.IllegalCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Qualifier("userService")
    @Autowired
    private UserDetailsService userDetailsService;

    public ResponseEntity<String> authorization(AuthorizationDTO authorizationDTO) throws IllegalCredentialsException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authorizationDTO.getUsername());
        Optional.of(userDetails).orElseThrow(IllegalCredentialsException::new);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, authorizationDTO.getPassword(), userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
