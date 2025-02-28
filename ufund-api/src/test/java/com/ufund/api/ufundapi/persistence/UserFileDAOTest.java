package com.ufund.api.ufundapi.persistence;
import com.ufund.api.ufundapi.model.User;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserFileDAOTest {

    private static final String TEST_FILE = "testUsers.json";

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private UserFileDAO userFileDAO;

    // runs a setup before each method is tested
    @BeforeEach
    void setUp() throws IOException {
        User[] mockUsers = { new User("Bob") };
        when(mockObjectMapper.readValue(any(File.class), eq(User[].class))).thenReturn(mockUsers);
        userFileDAO = new UserFileDAO(TEST_FILE, mockObjectMapper);
    }

    @Test
    void testLoadOnConstruction() {
        // test user get
        User[] users = userFileDAO.getUsers();
        assertEquals("Bob", users[0].getName(), "Loaded User is Bob");
    }

    @Test
    void testGetUser_Found() {
        // tests that the user exist
        User Bob = userFileDAO.getUser("Bob");
        assertNotNull(Bob, "Bob (1) should exist");
    }

    @Test
    void testGetUser_NotFound() {
         // tests that the user doesn't exist
        User bob = userFileDAO.getUser("Bob");
        assertNull(bob, "Should return null");
    }

    @Test
    void testCreateUser_Success() throws IOException {
        // tests that a new user can be created after setup
        User bob = new User("Bob");
        User createdUser = userFileDAO.createUser(bob);
        assertNotNull(createdUser, "Should return a new user");
        assertEquals("Bob", createdUser.getName(), "User must be Bob");
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(User[].class));
    }

    @Test
    void testCreateUser_AlreadyExists() throws IOException {
        // test if functionality works for creating a user that exists
        User existing = new User("Bob");
        User result = userFileDAO.createUser(existing);
        assertNull(result, "Should return null, user exist");
        verify(mockObjectMapper, never())
            .writeValue(any(File.class), any(User[].class)); // verify save didn't get called
    }

    @Test
    void testUpdateUser_Success() throws IOException {
        User updatedBob = new User("Bob");
        User result = userFileDAO.updateUser(updatedBob);
        assertNotNull(result, "Should return updated user");
        assertEquals("Bob", result.getName());
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(User[].class));
    }

    @Test
    void testUpdateUser_NotFound() throws IOException {
        // bob shouldn't exist
        User missingUser = new User("Bob");
        User result = userFileDAO.updateUser(missingUser);
        assertNull(result, "Should return null if the user doesn't exist");
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(User[].class));
    }

    @Test
    void testDeleteUser_Success() throws IOException {
        // Bob is in the data
        boolean deleted = userFileDAO.deleteUser("Bob");
        assertTrue(deleted, "Should return true for Delete");
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(User[].class));
        User result = userFileDAO.getUser("Bob");
        assertNull(result, "User Bob removed");
    }

    @Test
    void testDeleteUser_NotFound() throws IOException {
        // deletes user that doesn't eixst
        boolean deleted = userFileDAO.deleteUser("Bob");
        assertFalse(deleted, "Shouldn't return if user does not exist");
        verify(mockObjectMapper, never())
            .writeValue(any(File.class), any(User[].class));
    }
}
