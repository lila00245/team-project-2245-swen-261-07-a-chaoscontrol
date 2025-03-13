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
import com.ufund.api.ufundapi.service.AuthenticationService;
import com.ufund.api.ufundapi.service.RegistrationService;
import com.ufund.api.ufundapi.model.AuthUser;
import com.ufund.api.ufundapi.model.AuthRequest;

/**
 * Handles authentication and registration requests.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;
    private RegistrationService registrationService;

    /**
     * Constructs an AuthController that handles authentication and registration requests.
     * 
     * @param authenticationService The {@link AuthenticationService} used to authenticate users.
     * @param registrationService The {@link RegistrationService} used to register new users.
     */
    @Autowired
    public AuthController(AuthenticationService authenticationService, RegistrationService registrationService) {
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;
    }

    /**
     * Handles the POST request for user login.
     * 
     * @param authRequest The {@link AuthRequest} containing the username and password
     * @return A {@link ResponseEntity} containing the {@link AuthUser} object and HTTP status {@link HttpStatus#OK} if successful,
     *         or HTTP status {@link HttpStatus#NOT_FOUND} if the user does not exist.
     * @throws IOException when file cannot be accessed or read from
     */
    @PostMapping("/login")
    public ResponseEntity<AuthUser> login(@RequestBody AuthRequest authRequest) throws IOException {
        User authenticatedUser = authenticationService.authenticateUser(authRequest.getName(), authRequest.getPassword());
        if (authenticatedUser != null) {
            return new ResponseEntity<>(createAuthUser(authenticatedUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Use 401 for failed authentication
    }

    /**
     * Handles the POST request for user registration.
     *      * 
     * @param authRequest The {@link AuthRequest} containing the username and password
     * @return A {@link ResponseEntity} containing the {@link AuthUser} object and HTTP status {@link HttpStatus#CREATED} if successful,
     *         or HTTP status {@link HttpStatus#CONFLICT} if the user already exists.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthUser> register(@RequestBody AuthRequest authRequest) throws IOException {
        User newUser = registrationService.registerUser(authRequest.getName(), authRequest.getPassword());
        if (newUser != null) {
            return new ResponseEntity<>(createAuthUser(newUser), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 is fine here
    }

    /**
     * Creates an {@link AuthUser} object from the given {@link User} object.
     * 
     * @param user The {@link User} object to create an {@link AuthUser} from
     * @return The {@link AuthUser} object created from the {@link User} object
     */
    private AuthUser createAuthUser(User user) {
        return new AuthUser(user.getName(), user.getRole());
    }
}
