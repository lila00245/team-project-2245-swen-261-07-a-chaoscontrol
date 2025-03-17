package com.ufund.api.ufundapi.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

/**
 * Provides registration services for users.
 * <p>
 * {@literal @}Service annotation identifies this class as a Spring
 * 
 * @Author Team 1A - ChaosControl
 */
@Service
public class RegistrationService {

    private UserDAO userDAO;

    @Autowired
    public RegistrationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Registers a new user with the given name and password
     * @param name The name of the user
     * @param password The password of the user
     * @return The {@link User} object if registered, null otherwise
     * @throws IOException when file cannot be accessed or read from
     */
    public User registerUser(String name, String password, String role) throws IOException {
        if (name == null || password == null) {
            throw new IllegalArgumentException("Username or password cannot be null");
        }
        // Check if user already exists
        if (userDAO.getUser(name) != null) {
            return null;  // null if User already exists
        }

        // Create a new user if it doesn't exist
        User newUser = new User(name, password, role);
        return userDAO.createUser(newUser);  // Save the new user to the database
    }
}
