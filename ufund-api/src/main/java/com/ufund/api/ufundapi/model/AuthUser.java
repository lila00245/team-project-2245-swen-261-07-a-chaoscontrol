package com.ufund.api.ufundapi.model;

/**
 * Represents an authenticated user.
 * <p>
 * This object contains details about a user who has successfully passed the authentication process, 
 * such as the user's name and role. It is typically used to maintain the user's session or identify the user in the system.
 * 
 * @author Team 1A - ChaosControl
 */

public class AuthUser {
    private String name;
    private String role;

    /**
     * Create an authenticated user with the given name and role
     * @param name The name of the authenticated user
     * @param role The role of the authenticated user
     */
    public AuthUser(String name, String role) {
        this.name = name;
        this.role = role;
    }

    /**
     * Retrieves the name of the authenticated user
     * @return The name of the authenticated user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the authenticated user
     * @param name The name of the authenticated user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the role of the authenticated user
     * @return The role of the authenticated user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the authenticated user
     * @param role The role of the authenticated user
     */
    public void setRole(String role) {
        this.role = role;
    }
}
