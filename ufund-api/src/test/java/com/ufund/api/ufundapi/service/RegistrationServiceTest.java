
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
public class RegistrationServiceTest {
    
    private UserDAO mockUserDAO;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        mockUserDAO = mock(UserDAO.class);
        registrationService = new RegistrationService(mockUserDAO);
    }

    // @Test
    // void testRegisterUser() throws IOException {
    //     User user = new User("Danny", "password", "user");
    //     when(mockUserDAO.getUser(user.getName())).thenReturn(null);
    //     when(mockUserDAO.createUser(user)).thenReturn(user);
    //     User registeredUser = registrationService.registerUser("Danny", "password", "user");
    //     assertEquals("Danny", registeredUser.getName());
    // }

    @Test
    void testRegisterUserFailure() throws IOException {
        User user = new User("Danny", "password", "user");
        when(mockUserDAO.getUser(user.getName())).thenReturn(null);
        User registeredUser = registrationService.registerUser("Danny", "password", "user");
        assertNull(registeredUser);
    }
}
