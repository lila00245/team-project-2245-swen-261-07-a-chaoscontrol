package com.ufund.api.ufundapi.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

/**
 * Provides authentication services for users.
 * <p>
 * {@literal @}Service annotation identifies this class as a Spring
 * 
 * @Author Team 1A - ChaosControl
 */
@Service
public class AuthenticationService {

    private UserDAO userDAO;

    /**
     * Constructs an {@link AuthenticationService} with the specified {@link UserDAO}.
     * <br>
     * This constructor is used to inject the dependency for performing CRUD operations.
     *
     * @param userDAO The {@link UserDAO} to perform database operations for users
     */
    @Autowired
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Authenticates a user with the given name and password
     * @param name The name of the user
     * @param password The password of the user
     * @return The {@link User} object if authenticated, null otherwise
     * @throws IOException when file cannot be accessed or read from
     */
    public User authenticateUser(String name, String password) throws IOException {
        User user = userDAO.getUser(name);
        if (user != null && user.getPassword().equals(password)) {
            return user;  // If user exists and passwords match, return the user
        }
        return null;  // Authentication failed, return null
    }
}
