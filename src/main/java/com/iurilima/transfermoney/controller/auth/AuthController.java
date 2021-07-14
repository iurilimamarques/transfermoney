package com.iurilima.transfermoney.controller.auth;

import com.iurilima.transfermoney.controller.auth.business.AuthBusiness;
import com.iurilima.transfermoney.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthBusiness authBusiness;

    @PostMapping(path = "signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        return authBusiness.signinUser(loginRequest);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody User user) {
        return authBusiness.signupUser(user);
    }
}
