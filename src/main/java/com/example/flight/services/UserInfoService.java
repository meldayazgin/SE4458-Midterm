package com.example.flight.services;

import com.example.flight.models.User;
import com.example.flight.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(userInfo.getUsername(),userInfo.getPassword(), AuthorityUtils.createAuthorityList("ROLE_"  +userInfo.getRole()));

    }
}
