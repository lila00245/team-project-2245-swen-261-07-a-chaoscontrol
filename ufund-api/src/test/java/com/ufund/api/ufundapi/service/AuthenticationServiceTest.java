package com.ufund.api.ufundapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

@Tag("Service-Tier")
public class AuthenticationServiceTest {
    
    private UserDAO mockUserDAO;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        mockUserDAO = mock(UserDAO.class);
        authenticationService = new AuthenticationService(mockUserDAO);
    }

    @Test
    void testAuthenticateUser() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockUserDAO.getUser(user.getName())).thenReturn(user);
        User authenticatedUser = authenticationService.authenticateUser("Danny", "password", "user");
        assertEquals("Danny", authenticatedUser.getName());
    }

    @Test
    void testAuthenticateUserFailure() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockUserDAO.getUser(user.getName())).thenReturn(null);
        User authenticatedUser = authenticationService.authenticateUser("Danny", "password", "user");
        assertNull(authenticatedUser);
    }
}
