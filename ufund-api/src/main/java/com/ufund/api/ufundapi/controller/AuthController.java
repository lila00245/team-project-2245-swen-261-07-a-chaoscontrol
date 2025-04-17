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
     * @param user The {@link User} containing the username and password
     * @return A {@link ResponseEntity} containing the {@link User} object and HTTP status {@link HttpStatus#OK} if successful,
     *         or HTTP status {@link HttpStatus#NOT_FOUND} if the user does not exist.
     * @throws IOException when file cannot be accessed or read from
     */
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) throws IOException {
        LOG.info("POST /login " + user.getName() + " / " + user.getPassword() +" / ");        
        try {
            User authUser = authenticationService.authenticateUser(user.getName(), user.getPassword(),user.getRole());
            if (authUser != null){
                System.out.println(authUser);
                return new ResponseEntity<User>(authUser, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the POST request for user registration.
     * 
     * @param authRequest The {@link User} containing the user and password
     * @return A {@link ResponseEntity} containing the {@link User} object and HTTP status {@link HttpStatus#CREATED} if successful,
     *         or HTTP status {@link HttpStatus#CONFLICT} if the user already exists.
     */
    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) throws IOException {
        LOG.info("POST /register " + user.getName() + " / " + user.getPassword() + " / " + user.getRole());        try {
            User newUser = registrationService.registerUser(user.getName(), user.getPassword(), user.getRole() );
            if (newUser != null){
                System.out.println(newUser);
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
