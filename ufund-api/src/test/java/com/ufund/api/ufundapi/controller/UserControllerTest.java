package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
 * @author Owen, Lila
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock User DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException {  // getUser may throw IOException
        // Setup
        User user = new User("Owen");
        // When the same id is passed in, our mock User DAO will return the User object
        when(mockUserDAO.getUser(user.getName())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getName());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

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

    @Test
    public void testCreateUser() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("Owen");
        // when createUser is called, return true simulating successful
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("Owen");
        // when createUser is called, return false simulating failed
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

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

    @Test
    public void testUpdateUser() throws IOException { // updateUser may throw IOException
        // Setup
        User user = new User("Owen");
        // when updateUser is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);
        user.setName("Bob");

        // Invoke
        response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testUpdateUserFailed() throws IOException { // updateUser may throw IOException
        // Setup
        User user = new User("Bob");
        // when updateUser is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

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

    @Test
    public void testGetUsers() throws IOException { // getUsers may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User("Owen");
        users[1] = new User("The Great Iguana");
        // When getUsers is called return the Users created above
        when(mockUserDAO.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws IOException { // getUsers may throw IOException
        // Setup
        // When getUsers is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException { // deleteUser may throw IOException
        // Setup
        String name = "Owen";
        // when deleteUser is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(name)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }


    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteUser may throw IOException
        // Setup
        String name = "Owen";
        // when deleteUser is called return false, simulating failed deletion
        when(mockUserDAO.deleteUser(name)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException { // deleteUser may throw IOException
        // Setup
        String name = "Owen";
        // When deleteUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(name);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
