package com.iurilima.transfermoney.controller.user;

import com.iurilima.transfermoney.model.QUser;
import com.iurilima.transfermoney.model.User;
import com.iurilima.transfermoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public ResponseEntity<Object> findUser(@PathVariable("username") String username) {
        List<User> users = (List<User>) userRepository.findAll(QUser.user.username.contains(username));

        return ResponseEntity.ok(users);
    }
}
