package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyByte;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the User class
 * 
 * @author Lila
 */
@Tag("Model-Tier")
public class UserTest {

    @Test
    public void testCtor() {
        // setup
        String expectedName = "Denny";

        // invoke
        User user = new User(expectedName);

        // analyze
        assertEquals(expectedName, user.getName());
        assertEquals(new ArrayList<>(), user.getBasket());
    }

    @Test
    public void testName() {
        // setup
        String name = "Denny";
        User user = new User(name);

        String expected_name = "Danny";

        // invoke
        user.setName(expected_name);

        // analyze
        assertEquals(expected_name, user.getName());
    }

    @Test
    public void testBasket() {
        // setup
        int userID = 99;
        User user = new User("Denny");
        Need need = new Need(userID, "apple", 10, "fruit");

        ArrayList<Need> expectedBasket = new ArrayList<>();
        
        // invoke
        user.addToBasket(need);

        // analyze
        assertEquals(expectedBasket, user.getBasket());
        assertEquals(need, user.getBasket().get(0));
    }

    @Test
    public void testToString() {
        // setup
        String userName = "Denny";
        ArrayList<Need> basket = new ArrayList<>();
        String expected_string = String.format(User.STRING_FORMAT, userName, basket);
        User user = new User(userName);

        // invoke
        String actual_string = user.toString();

        // analyze
        assertEquals(expected_string, actual_string);
    }



    
}
