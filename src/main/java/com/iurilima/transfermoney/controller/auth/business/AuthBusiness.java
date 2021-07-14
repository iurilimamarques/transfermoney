package com.iurilima.transfermoney.controller.auth.business;

import com.iurilima.transfermoney.config.jwt.JwtUtils;
import com.iurilima.transfermoney.config.service.LoggedUser;
import com.iurilima.transfermoney.controller.auth.JwtResponseDto;
import com.iurilima.transfermoney.controller.auth.LoginRequestDto;
import com.iurilima.transfermoney.model.QUser;
import com.iurilima.transfermoney.model.User;
import com.iurilima.transfermoney.repository.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthBusiness {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<Object> signupUser(User user) {
        try {
            if (userRepository.exists(QUser.user.username.eq(user.getUsername())))
                throw new ValidationException("This e-mail address is already being used");

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setEmail(user.getUsername())
                    .setName(user.getFullName())
                    .build();

            Customer customer = Customer.create(customerCreateParams);
            user.setStripeIndentifier(customer.getId());

            user.setAuthorities("ROLE_USER");

            userRepository.save(user);

            return ResponseEntity.ok().build();
        } catch (ValidationException | StripeException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("response", ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Object> signinUser(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        LoggedUser userDetails = (LoggedUser) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDto(jwt, userDetails.getUserId(), userDetails.getUsername(), userDetails.getExpirationTime(), roles));
    }
}
