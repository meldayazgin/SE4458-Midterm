package com.example.flight.controllers;

import com.example.flight.auth.JwtUtility;
import com.example.flight.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private JwtUtility jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/v1/authenticate")
    public String authenticateAndGetToken(@RequestBody UserRequest authRequest) {
        try {
            PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
            bcryptEncoder.encode(authRequest.getPassword());
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                            authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUserName());
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }


    }
}