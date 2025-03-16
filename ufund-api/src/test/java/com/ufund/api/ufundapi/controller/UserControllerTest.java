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

 * Test the User Controller class
 * 
 * @author Owen
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;


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
        User user = new User("Owen");
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

    public void testGetUserNotFound() throws Exception { // createUser may throw IOException
        // Setup
        String name = "Owen";
        // When the same id is passed in, our mock User DAO will return null, simulating
        // no User found
        when(mockUserDAO.getUser(name)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(name);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    /**
     * Tests the 'getUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test

    public void testGetUserHandleException() throws Exception { // createUser may throw IOException
        // Setup
        String name = "Owen";
        // When getUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(name);

        // Invoke
        ResponseEntity<User> response = userController.getUser(name);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************

     * The following tests will fail until all UserController methods
     * are implemented.
     ****************************************************************/

    @Test

    public void testCreateUser() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("Owen");
        // when createUser is called, return true simulating successful

        // creation and save

        when(mockUserDAO.createUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Tests the 'createUser' method when user creation fails.
     * Verifies that a 409 CONFLICT response is returned when user creation fails.
     */
    @Test

    public void testCreateUserFailed() throws IOException {  // createUser may throw IOException
        // Setup

        User user = new User("Owen");
        when(mockUserDAO.createUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Tests the 'createUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test

    public void testCreateUserHandleException() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("Owen");

        // When createUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    /**
     * Tests the 'updateUser' method in UserController.
     * Verifies that a valid User object returns the updated User and an HTTP 200 OK response.
     */
    @Test

    public void testUpdateUser() throws IOException { // updateUser may throw IOException
        // Setup

        User user = new User("Owen");
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

    public void testUpdateUserFailed() throws IOException { // updateUser may throw IOException
        // Setup

        User user = new User("Bob");
        when(mockUserDAO.updateUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'updateUser' method when an exception occurs.
     * Verifies that a 500 INTERNAL SERVER ERROR response is returned when an exception is thrown.
     */
    @Test

    public void testUpdateUserHandleException() throws IOException { // updateUser may throw IOException
        // Setup
        User user = new User("Owen");
        // When updateUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    /**
     * Tests the 'getUsers' method in UserController.
     * Verifies that the correct list of Users is returned along with an HTTP 200 OK response.
     */
    @Test

    public void testGetUsers() throws IOException { // getUsers may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User("Owen");
        users[1] = new User("The Great Iguana");

        // When getUsers is called return the Users created above

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

    public void testGetUsersHandleException() throws IOException { // getUsers may throw IOException
        // Setup
        // When getUsers is called on the Mock User DAO, throw an IOException


        doThrow(new IOException()).when(mockUserDAO).getUsers();
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'deleteUser' method in UserController.
     * Verifies that the user is successfully deleted with an HTTP 200 OK response.
     */
    @Test

    public void testDeleteUser() throws IOException { // deleteHero may throw IOException
        // Setup
        String name = "Owen";
        User user = new User(name);
        when(mockUserDAO.createUser(user)).thenReturn(user);
        ResponseEntity<User> response1 = userController.createUser(user);
        // when deleteHero is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(name)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteHero may throw IOException
        // Setup
        String name = "Owen";
        // when deleteHero is called return false, simulating failed deletion

        when(mockUserDAO.deleteUser(name)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);


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

    @Test
    public void testDeleteUserHandleException() throws IOException { // deleteHero may throw IOException
        // Setup
        String name = "Owen";
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(name);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


}
