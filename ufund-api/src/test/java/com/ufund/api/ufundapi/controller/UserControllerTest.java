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
 * Test the Hero Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new HeroController object and inject
     * a mock Hero DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException {  // getHero may throw IOException
        // Setup
        User user = new User("Owen");
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockUserDAO.getUser(user.getName())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getName());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // createHero may throw IOException
        // Setup
        String name = "Owen";
        // When the same id is passed in, our mock Hero DAO will return null, simulating
        // no hero found
        when(mockUserDAO.getUser(name)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(name);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createHero may throw IOException
        // Setup
        String name = "Owen";
        // When getHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(name);

        // Invoke
        ResponseEntity<User> response = userController.getUser(name);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all HeroController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateUser() throws IOException {  // createHero may throw IOException
        // Setup
        User user = new User("Owen");
        // when createHero is called, return true simulating successful
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {  // createHero may throw IOException
        // Setup
        User user = new User("Owen");
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateHeroHandleException() throws IOException {  // createHero may throw IOException
        // Setup
        User user = new User("Owen");

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateHero() throws IOException { // updateHero may throw IOException
        // Setup
        User user = new User("Owen");
        // when updateHero is called, return true simulating successful
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
    public void testUpdateUserFailed() throws IOException { // updateHero may throw IOException
        // Setup
        User user = new User("Bob");
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateUserHandleException() throws IOException { // updateHero may throw IOException
        // Setup
        User user = new User("Owen");
        // When updateHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException { // getHeroes may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User("Owen");
        users[1] = new User("The Great Iguana");
        // When getHeroes is called return the heroes created above
        when(mockUserDAO.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws IOException { // getHeroes may throw IOException
        // Setup
        // When getHeroes is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


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

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
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
