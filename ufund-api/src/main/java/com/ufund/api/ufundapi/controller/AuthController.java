package com.ufund.api.ufundapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.service.AuthService;
import com.ufund.api.ufundapi.model.AuthUser;
import com.ufund.api.ufundapi.model.AuthRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;  

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUser> login(@RequestBody AuthRequest authRequest) throws IOException {
        // Use the LoginService instance to authenticate the user
        User authenticatedUser = authService.authenticateUser(authRequest.getName(), authRequest.getPassword());
    
        if (authenticatedUser != null) {
            // Create AuthUser with username and role only (exclude password)
            AuthUser authUser = new AuthUser(authenticatedUser.getName(), authenticatedUser.getRole());  // Adjusted constructor
            return new ResponseEntity<>(authUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
        
    @PostMapping("/register")
    public ResponseEntity<AuthUser> register(@RequestBody AuthRequest authRequest) throws IOException {
        User newUser = authService.registerUser(authRequest.getName(), authRequest.getPassword());

        if (newUser != null) {
            AuthUser authUser = new AuthUser(newUser.getName(), newUser.getRole());  
            return new ResponseEntity<>(authUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
