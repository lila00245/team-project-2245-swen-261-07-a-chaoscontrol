package com.ufund.api.ufundapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.service.AuthenticationService;
import com.ufund.api.ufundapi.service.RegistrationService;
import com.ufund.api.ufundapi.model.AuthRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles authentication and registration requests.
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private AuthenticationService authenticationService;
    private RegistrationService registrationService;

    /**
     * Constructs an AuthController that handles authentication and registration requests.
     * 
     * @param authenticationService The {@link AuthenticationService} used to authenticate users.
     * @param registrationService The {@link RegistrationService} used to register new users.
     */
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
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody AuthRequest authRequest) throws IOException {
        LOG.info("POST /login " + authRequest.getName() + " / " + authRequest.getPassword() +" / ");        
            try {
            User authUser = authenticationService.authenticateUser(authRequest.getName(), authRequest.getPassword(),authRequest.getRole());
            if (authUser != null){
                System.out.println(authUser);
                return new ResponseEntity<User>(authUser, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the POST request for user registration.
     *      * 
     * @param authRequest The {@link AuthRequest} containing the username and password
     * @return A {@link ResponseEntity} containing the {@link AuthUser} object and HTTP status {@link HttpStatus#CREATED} if successful,
     *         or HTTP status {@link HttpStatus#CONFLICT} if the user already exists.
     */
    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody AuthRequest authRequest) throws IOException {
        LOG.info("POST /register " + authRequest.getName() + " / " + authRequest.getPassword() + " / " + authRequest.getRole());        try {
            User newUser = registrationService.registerUser(authRequest.getName(), authRequest.getPassword(), authRequest.getRole() );
            if (newUser != null){
                System.out.println(newUser);
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
