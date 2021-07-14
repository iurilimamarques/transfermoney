package com.iurilima.transfermoney.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Full name should not be null")
    @NotBlank(message = "Full name should not be null")
    @Column(name = "full_name", length = 100, nullable = false)
    @Size(max = 100, message = "The full name should not be longer than 100 characters")
    private String fullName;

    @NotNull(message = "E-mail should not be null")
    @NotBlank(message = "E-mail should not be null")
    @Email(message = "E-mail is not valid")
    @Column(name = "email", length = 100, nullable = false)
    @Size(max = 100, message = "The e-mail should not be longer than 100 characters")
    private String username;

    @NotNull(message = "Password should not be null")
    @NotBlank(message = "Password should not be null")
    @Column(nullable = false)
    private String password;

    @Column(name = "stripe_identifier", nullable = false)
    private String stripeIndentifier;

    @Column(nullable = false)
    private String authorities;

    public User(Long id, String fullName, String password, String authorities) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
    }

    public User() {

    }
}
