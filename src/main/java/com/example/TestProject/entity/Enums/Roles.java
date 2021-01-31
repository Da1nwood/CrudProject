package com.example.TestProject.entity.Enums;

import org.springframework.security.core.GrantedAuthority;


public enum Roles implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_HOSPITAL;

    @Override
    public String getAuthority() {
        return name();
    }
}

