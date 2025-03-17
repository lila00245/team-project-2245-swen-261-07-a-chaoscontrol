package com.ufund.api.ufundapi.persistence;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the UserFileDAO class.
 * This class tests the functionality of the UserFileDAO, including loading, 
 * retrieving, updating, creating, and deleting 'User' entities in a file.
 * 
 * @authors Jay & Vlad
 */
@Tag("Persistence-Tier")
@ExtendWith(MockitoExtension.class)
public class UserFileDAOTest {

    private static final String TEST_FILE = "testUsers.json";

    @Mock
    private ObjectMapper mockObjectMapper;
    private UserFileDAO userFileDAO;
    private User[] mockUsers;

    /**
     * Sets up the test environment by mocking the necessary dependencies 
     * and initializing the UserFileDAO instance before each test.
     *
     * @throws IOException if there is an issue reading the test data from the file.
     */
    @BeforeEach
    void setUp() throws IOException {
        setupTestUsers();
        when(mockObjectMapper.readValue(any(File.class), eq(User[].class))).thenReturn(mockUsers);
        userFileDAO = new UserFileDAO(TEST_FILE, mockObjectMapper);
    }

    /**
     * Helper method to set up test users with mock data.
     * Adds users with their associated baskets of needs for testing.
     */
    private void setupTestUsers() {
        ArrayList<Need> bobBasket = new ArrayList<>();
        ArrayList<Need> haroldBasket = new ArrayList<>();
        bobBasket.add(new Need(0, "Apples", 4.50, "Fruit"));
        haroldBasket.add(new Need(1, "Beans", 5.50, "Vegetable"));
        haroldBasket.add(new Need(2, "Carrots", 3.32, "Vegetable"));
        mockUsers = new User[]{
            new User("Bob", "password", bobBasket),
            new User("Harold", "password", haroldBasket)
        };
    }

    /**
     * Tests that the UserFileDAO correctly loads user data on construction.
     * Verifies that the loaded users have the correct names and count.
     */
    @Test
    void testLoadOnConstruction() {
        User[] users = userFileDAO.getUsers();
        assertEquals("Bob", users[0].getName(), "Loaded User is Bob");
        assertEquals("Harold", users[1].getName(), "Loaded User is Harold");
    }

    /**
     * Tests the 'getUser' method of the UserFileDAO.
     * Verifies that it correctly retrieves an existing user.
     */
    @Test
    void testGetUser() {
        assertNotNull(userFileDAO.getUser("Bob"), "Bob should exist");
        assertNotNull(userFileDAO.getUser("Harold"), "Harold should exist");
    }

    /**
     * Tests the 'getUser' method of the UserFileDAO when the user does not exist.
     * Verifies that it returns null for non-existing users.
     */
    @Test
    void testGetUserNotFound() {
        assertNull(userFileDAO.getUser("Greg"), "Greg should not exist");
    }

    /**
     * Tests the 'createUser' method of the UserFileDAO.
     * Verifies that it correctly creates a new user and writes to the file.
     *
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testCreateUser() throws IOException {
        User newUser = new User("Bill", "password");
        User createdUser = userFileDAO.createUser(newUser);
        assertNotNull(createdUser, "Should return a new user");
        assertEquals("Bill", createdUser.getName(), "User must be Bill");
        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(User[].class));
    }

    /**
     * Tests the 'createUser' method when attempting to create a user that already exists.
     * Verifies that it returns null if the user is not unique.
     *
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testCreateUserNotUnique() throws IOException {
        User existingUser = new User("Bob", "password");
        User createdUser = userFileDAO.createUser(existingUser);
        assertNull(createdUser, "Should return null when user already exists");
    }

    /**
     * Tests the 'updateUser' method of the UserFileDAO.
     * Verifies that it correctly updates an existing user's information.
     *
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testUpdateUser() throws IOException {
        User updatedUser = new User("Harold", "password");
        User result = userFileDAO.updateUser(updatedUser);
        assertNotNull(result, "Should return updated Harold");
        assertEquals("Harold", result.getName());
        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(User[].class));
    }

    /**
     * Tests the 'updateUser' method when attempting to update a non-existent user.
     * Verifies that it returns null when the user does not exist.
     *
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testUpdateUserNotFound() throws IOException {
        User nonExistentUser = new User("Greg", "password");
        User result = userFileDAO.updateUser(nonExistentUser);
        assertNull(result, "Should return null when user does not exist");
    }

    /**
     * Tests the 'deleteUser' method of the UserFileDAO.
     * Verifies that it correctly deletes an existing user and returns true.
     *
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testDeleteUser() throws IOException {
        boolean deleted = userFileDAO.deleteUser("Bob");
        assertTrue(deleted, "Should return true when 'Bob' is deleted");
        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(User[].class));
        assertNull(userFileDAO.getUser("Bob"), "User 'Bob' should be removed");
    }

    /**
     * Tests the 'deleteUser' method when attempting to delete a non-existent user.
     * Verifies that it returns false when the user does not exist.
     *
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testDeleteUserNotFound() throws IOException {
        boolean deleted = userFileDAO.deleteUser("Greg");
        assertFalse(deleted, "Should return false when user does not exist");
    }

    /**
     * Tests the constructor of the UserFileDAO when an exception occurs during file reading.
     * Verifies that it throws an IOException when there is an issue initializing the DAO.
     *
     * @throws IOException if there is an issue with file reading.
     */
    @Test
    void testConstructorException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper).readValue(any(File.class), eq(User[].class));
        assertThrows(IOException.class, () -> new UserFileDAO(TEST_FILE, mockObjectMapper), "IOException not thrown");
    }
}
