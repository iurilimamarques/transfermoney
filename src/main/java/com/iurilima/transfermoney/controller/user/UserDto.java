package com.iurilima.transfermoney.controller.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {

    private String fullName;

    private String username;

    UserDto() {}
}
