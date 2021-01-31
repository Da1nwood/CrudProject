package com.example.TestProject.entity;

import com.example.TestProject.entity.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@ToString(exclude = "account")
@Table(name = "accountCredentials")
public class AccountCredentials implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "username", unique = true, length = 16)
    private String username;

    @NotNull
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @UniqueElements
    @JsonIgnore
    private Set<Roles> authorities = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;

    @JsonIgnore
    private boolean accountNonExpired;
    @JsonIgnore
    private boolean accountNonLocked;
    @JsonIgnore
    private boolean credentialsNonExpired;
    @JsonIgnore
    private boolean enabled;


}
