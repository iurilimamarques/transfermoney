package com.iurilima.transfermoney.config;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private final String role;

    public Authority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
