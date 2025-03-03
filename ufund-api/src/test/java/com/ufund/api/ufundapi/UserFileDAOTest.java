package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserFileDAO;

@Tag("Model-tier")
public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    ObjectMapper mockObjectMapper;
    User[] testUsers;

    @BeforeEach
    public void setupUserFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        setupTestUsers();
        when(mockObjectMapper.readValue(new File("Nothing.txt"), User[].class)).thenReturn(testUsers);
        userFileDAO = new UserFileDAO("Nothing.txt", mockObjectMapper);
    }
    private void setupTestUsers(){
        // Create a basket for each user
        ArrayList<Need> haroldBasket = new ArrayList<>();
        ArrayList<Need> maryBasket = new ArrayList<>();
        ArrayList<Need> alexBasket = new ArrayList<>();
        haroldBasket.add(new Need(1, "Beans", 5.50, "Vegetable"));
        haroldBasket.add(new Need(2, "Carrots", 3.32, "Vegetable"));
        maryBasket.add(new Need(3, "Beef", 15.30, "Protein"));
        maryBasket.add(new Need(4, "Rice", 3.20, "Grain"));
        alexBasket.add(new Need(5, "Pork", 13.20, "Protein"));
        testUsers = new User[3];
        testUsers[0] = new User("Harold", haroldBasket);
        testUsers[1] = new User("Mary", maryBasket);
        testUsers[2] = new User("Alex", alexBasket);
    }

    @Test
    public void testConstructorException() throws IOException{
        //ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException()).when(mockObjectMapper).readValue(new File("Nothing.txt"), User[].class);
        assertThrows(IOException.class, () -> new UserFileDAO("Nothing.txt", mockObjectMapper), "IOException not thrown");
    }

    @Test
    public void testGetUserHarold(){
        User expectedUser = testUsers[0];
        User actualUser = userFileDAO.getUser("Harold");
        assertEquals(expectedUser, actualUser, "Harold was not the actual user");
    }

    @Test
    public void testCreateUser() throws IOException{
        User newUser = new User("Greg");
        userFileDAO.createUser(newUser);
        User createdUser = userFileDAO.getUser("Greg");
        assertEquals(newUser.getName(), createdUser.getName(), "User was not created");
    }

    @Test
    public void testUpdateUser() throws IOException{
        ArrayList<Need> alexNewBasket = new ArrayList<>();
        alexNewBasket.add(new Need(6, "Chicken", 10.30, "Poultry"));
        User alexUpdated = new User("Alex", alexNewBasket);
        userFileDAO.updateUser(alexUpdated);
        User alex = userFileDAO.getUser("Alex");
        assertEquals(alexUpdated.getName(), alex.getName(), "User name is not Alex");
        assertEquals(alexUpdated.getBasket(), alex.getBasket(), "Baskets are not equal");
    }

    @Test
    public void testDeleteUser() throws IOException{
        userFileDAO.deleteUser("Alex");
        User alex = userFileDAO.getUser("Alex");
        assertNull(alex, "Alex should have been deleted");
    }
}
