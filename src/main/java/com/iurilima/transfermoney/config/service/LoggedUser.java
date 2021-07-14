package com.iurilima.transfermoney.config.service;

import com.iurilima.transfermoney.config.Authority;
import com.iurilima.transfermoney.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LoggedUser implements UserDetails {

    private User user;
    private List<Authority> authorities;
    private Date expirationTime;

    public LoggedUser(User user, Date expirationTime) {
        this.user = user;
        this.authorities = Arrays.stream(user.getAuthorities().split(","))
                .map(Authority::new)
                .collect(Collectors.toList());
        this.expirationTime = expirationTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return expirationTime.getTime() > new Date().getTime();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return user.getId();
    }

    public Date getExpirationTime() {
        return expirationTime;
    }
}
