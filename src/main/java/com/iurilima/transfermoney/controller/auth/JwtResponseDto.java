package com.iurilima.transfermoney.controller.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JwtResponseDto {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private Date expirationTime;
    private List<String> roles;

    public JwtResponseDto(String token, Long id, String username, Date expirationTime, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.expirationTime = expirationTime;
        this.roles = roles;
    }
}
