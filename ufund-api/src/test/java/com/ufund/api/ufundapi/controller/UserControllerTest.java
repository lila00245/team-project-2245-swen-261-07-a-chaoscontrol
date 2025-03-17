package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the {@link UserController} class.
 * 
 * @author Owen
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Setup the UserController and mock UserDAO before each test.
     * This method is executed before each test method.
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    /**
     * Tests the 'getUser' method in UserController.
     * Verifies that a valid User name returns the correct User object and an HTTP 200 OK response.
     */
    @Test
    public void testGetUser() throws IOException {
        User user = new User("Owen", "password","user");
        when(mockUserDAO.getUser(user.getName())).thenReturn(user);
        ResponseEntity<User> response = userController.getUser(user.getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Tests the 'getUser' method when the user is not found.
     * Verifies that a 404 NOT FOUND response is returned when no user is found.
     */
    @Test
    public void testGetUserNotFound() throws Exception {
        String name = "Owen";
        when(mockUserDAO.getUser(name)).thenReturn(null);
        ResponseEntity<User> response = userController.getUser("Owen");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'getUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test
    public void testGetUserHandleException() throws Exception {
        doThrow(new IOException()).when(mockUserDAO).getUser("Owen");
        ResponseEntity<User> response = userController.getUser("Owen");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'createUser' method in UserController.
     * Verifies that a valid User object returns the correct User and an HTTP 200 OK response.
     */
    @Test
    public void testCreateUser() throws IOException {
        User user = new User("Owen", "password","user");
        when(mockUserDAO.createUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Tests the 'createUser' method when user creation fails.
     * Verifies that a 409 CONFLICT response is returned when user creation fails.
     */
    @Test
    public void testCreateUserFailed() throws IOException {
        User user = new User("Owen", "password","user");
        when(mockUserDAO.createUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Tests the 'createUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test
    public void testCreateUserHandleException() throws IOException {
        User user = new User("Owen", "password","user");
        doThrow(new IOException()).when(mockUserDAO).createUser(user);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    /**
     * Tests the 'updateUser' method in UserController.
     * Verifies that a valid User object returns the updated User and an HTTP 200 OK response.
     */
    @Test
    public void testUpdateUser() throws IOException {
        User user = new User("Owen", "password","user");
        when(mockUserDAO.updateUser(user)).thenReturn(user);
        user.setName("Bob");
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Tests the 'updateUser' method when user update fails.
     * Verifies that a 404 NOT FOUND response is returned when user update fails.
     */
    @Test
    public void testUpdateUserFailed() throws IOException {
        User user = new User("Bob", "password","user");
        when(mockUserDAO.updateUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'updateUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test
    public void testUpdateUserHandleException() throws IOException {
        User user = new User("Owen", "password","user");
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    /**
     * Tests the 'getUsers' method in UserController.
     * Verifies that the correct list of Users is returned along with an HTTP 200 OK response.
     */
    @Test
    public void testGetUsers() throws IOException {
        User[] users = new User[2];
        users[0] = new User("Owen", "password","user");
        users[1] = new User("The Great Iguana", "password","user");
        when(mockUserDAO.getUsers()).thenReturn(users);
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    /**
     * Tests the 'getUsers' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test
    public void testGetUsersHandleException() throws IOException {
        doThrow(new IOException()).when(mockUserDAO).getUsers();
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'deleteUser' method in UserController.
     * Verifies that the user is successfully deleted with an HTTP 200 OK response.
     */
    @Test
    public void testDeleteUser() throws IOException {
        when(mockUserDAO.deleteUser("Owen")).thenReturn(true);
        ResponseEntity<User> response = userController.deleteUser("Owen");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests the 'deleteUser' method when user deletion fails.
     * Verifies that a 404 NOT FOUND response is returned when the user is not found.
     */
    @Test
    public void testDeleteUserNotFound() throws IOException {
        when(mockUserDAO.deleteUser("Owen")).thenReturn(false);
        ResponseEntity<User> response = userController.deleteUser("Owen");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'deleteUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test
    public void testDeleteUserHandleException() throws IOException {
        doThrow(new IOException()).when(mockUserDAO).deleteUser("Owen");
        ResponseEntity<User> response = userController.deleteUser("Owen");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
