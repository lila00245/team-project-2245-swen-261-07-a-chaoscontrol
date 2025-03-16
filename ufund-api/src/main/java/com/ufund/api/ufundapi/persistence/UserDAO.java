package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.User;

/**
 * Defines the interface for User object persistence
 * 
 * @author Team 1A - ChaosControl
 */
public interface UserDAO {
    /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a {@linkplain User user} with the given name
     * 
     * @param name The name of the {@link User user} to get
     * 
     * @return a {@link User user} object with the matching name
     * <br>
     * null if no {@link User user} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(String name) throws IOException;

    /**
     * Creates and saves a {@linkplain User user}
     * 
     * @param User {@linkplain User user} object to be created and saved
     * <br>
     * The name of the User object is ignored and a new unique name is assigned
     *
     * @return The new {@link User} object if successful, or null if the user could not be created
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

    /**
     * Updates and saves a {@linkplain User user}
     * 
     * @param {@link User user} object to be updated and saved
     * 
     * @return updated {@link User user} if successful, null if
     * {@link User user} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * Deletes a {@linkplain User user} with the given name
     * 
     * @param name The user of the {@link User user}
     * 
     * @return true if the {@link User user} was deleted
     * <br>
     * false if User with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(String name) throws IOException;
}
