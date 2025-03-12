package com.ufund.api.ufundapi.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

@Service
public class AuthService {

    private UserDAO userDAO;

    @Autowired
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticateUser(String name, String password) throws IOException {
        User user = userDAO.getUser(name);
        
        if (user != null && user.getPassword().equals(password)) {
            return user;  // If user exists and passwords match, return the user
        }
        return null;  // Authentication failed, return null
    }

    public User registerUser(String name, String password) throws IOException {
        // Check if user already exists
        if (userDAO.getUser(name) != null) {
            return null;  // null if User already exists
        }

        // Create a new user if it doesn't exist
        User newUser = new User(name);
        newUser.setPassword(password);  // Set the user's password

        return userDAO.createUser(newUser);  // Save the new user to the database
    }

}
