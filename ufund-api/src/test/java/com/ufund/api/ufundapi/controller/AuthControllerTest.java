package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.service.AuthenticationService;
import com.ufund.api.ufundapi.service.RegistrationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Tag("Controller-Tier")
public class AuthControllerTest {
    private AuthController authController;
    private AuthenticationService mockAuthenticationService;
    private RegistrationService mockRegistrationService;

    @BeforeEach
    public void setupAuthController() {
        mockAuthenticationService = mock(AuthenticationService.class);
        mockRegistrationService = mock(RegistrationService.class);
        authController = new AuthController(mockAuthenticationService, mockRegistrationService);
    }

    @Test
    public void testLogin() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockAuthenticationService.authenticateUser(user.getName(), user.getPassword(), user.getRole())).thenReturn(user);
        ResponseEntity<User> response = authController.login(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testLoginNotFound() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockAuthenticationService.authenticateUser(user.getName(), user.getPassword(), user.getRole())).thenReturn(null);
        ResponseEntity<User> response = authController.login(user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testLoginHandleException() throws IOException {
        User user = new User("Danny", "password", "user");
        doThrow(new IOException()).when(mockAuthenticationService).authenticateUser(user.getName(), user.getPassword(), user.getRole());
        ResponseEntity<User> response = authController.login(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRegister() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockRegistrationService.registerUser(user.getName(), user.getPassword(), user.getRole())).thenReturn(user);
        ResponseEntity<User> response = authController.register(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testRegisterConflict() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockRegistrationService.registerUser(user.getName(), user.getPassword(), user.getRole())).thenReturn(null);
        ResponseEntity<User> response = authController.register(user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testRegisterHandleException() throws IOException {
        User user = new User("Danny", "password", "user");
        doThrow(new IOException()).when(mockRegistrationService).registerUser(user.getName(), user.getPassword(), user.getRole());
        ResponseEntity<User> response = authController.register(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}