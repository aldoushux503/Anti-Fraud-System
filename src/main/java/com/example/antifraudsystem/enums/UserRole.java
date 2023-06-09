package com.example.antifraudsystem.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    MERCHANT, ADMINISTRATOR, SUPPORT;

    @Override
    public String getAuthority() {
        return name();
    }
}
