package com.ufund.api.ufundapi.persistence;
import com.ufund.api.ufundapi.model.User;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserFileDAOTest {

    private static final String TEST_FILE = "testUsers.json";

    @Mock
    private ObjectMapper mockObjectMapper;
    private UserFileDAO userFileDAO;

    // runs a setup before each method is tested
    @BeforeEach
    void setUp() throws IOException {
        // We mock the file load to return a single existing user, "Bob"
        User[] mockUsers = { new User("Bob") };
        when(mockObjectMapper.readValue(any(File.class), eq(User[].class))).thenReturn(mockUsers);

        userFileDAO = new UserFileDAO(TEST_FILE, mockObjectMapper);
    }

    @Test
    void testLoadOnConstruction() {
        User[] users = userFileDAO.getUsers();
        assertEquals("Bob", users[0].getName(), "Loaded User is Bob");
    }

    @Test
    void testGetUser() {
        User Bob = userFileDAO.getUser("Bob");
        assertNotNull(Bob, "Bob should exist");
    }

    @Test
    void testCreateUser() throws IOException {
        User newUser = new User("Bill");
        User createdUser = userFileDAO.createUser(newUser);
        assertNotNull(createdUser, "Should return a new user");
        assertEquals("Bill", createdUser.getName(), "User must be Bill");
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(User[].class));
    }

    @Test
    void testCreateUserException() throws IOException {
        User existing = new User("Bob");
        User result = userFileDAO.createUser(existing);
        assertNull(result, "Should return null, user 'Bob' already exists");
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(User[].class));
    }

    @Test
    void testUpdateUser() throws IOException {
        User updatedBob = new User("Bob");
        User result = userFileDAO.updateUser(updatedBob);
        assertNotNull(result, "Should return updated user");
        assertEquals("Bob", result.getName());
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(User[].class));
    }

    @Test
    void testDeleteUser() throws IOException {
        boolean deleted = userFileDAO.deleteUser("Bob");
        assertTrue(deleted, "Should return true when 'Bob' is deleted");
        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(User[].class));
        User result = userFileDAO.getUser("Bob");
        assertNull(result, "User 'Bob' should be removed");
    }
}