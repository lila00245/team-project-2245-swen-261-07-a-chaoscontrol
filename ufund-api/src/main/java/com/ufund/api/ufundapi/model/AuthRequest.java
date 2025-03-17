package com.ufund.api.ufundapi.model;

/**
 * Represents an authentication request.
 * <p>
 * This class is used to capture the necessary details (username and password) for a user to authenticate.
 * Typically used for login or registration processes.
 * 
 * @author Team 1A - ChaosControl
 */
public class AuthRequest {
    private String name;
    private String password;

    /**
     * Create an authentication request with the given name and password
     * @param name The name of the user
     * @param password The password of the user
     */
    public AuthRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Retrieves the name of the user
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     * @param name The name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password The password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
