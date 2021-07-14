package com.iurilima.transfermoney.config.service;

import com.iurilima.transfermoney.model.QUser;
import com.iurilima.transfermoney.model.User;
import com.iurilima.transfermoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.iurilima.transfermoney.config.SecurityConstants.EXPIRATION_TIME;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoggedUser loggedUser = null;
        try {
            User user = (User) userRepository
                    .findOne(QUser.user.username.eq(username))
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            loggedUser = new LoggedUser(user, new Date((new Date()).getTime() + EXPIRATION_TIME));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return loggedUser;
    }
}
