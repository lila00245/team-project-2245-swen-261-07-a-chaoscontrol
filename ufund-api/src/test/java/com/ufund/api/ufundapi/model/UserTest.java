package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the User class.
 * Verifies the functionality of User methods.
 * 
 * @author Lila, Owen
 */
@Tag("Model-Tier")
public class UserTest {

    /**
     * Tests the constructor of the User class.
     * Verifies that the User is correctly initialized with a name and an empty basket.
     */
    @Test
    public void testCtor() {
        User user = new User("Denny", "password","user");
        assertEquals("Denny", user.getName());
        assertEquals(new ArrayList<>(), user.getBasket());
    }

    /**
     * Tests the 'setName' method in the User class.
     * Verifies that the user's name is correctly updated.
     */
    @Test
    public void testName() {
        User user = new User("Denny", "password","user");
        user.setName("Danny");
        assertEquals("Danny", user.getName());
    }

    /**
     * Tests the 'addToBasket' method in the User class.
     * Verifies that a Need is correctly added to the user's basket.
     */
    @Test
    public void testBasket() {
        User user = new User("Denny", "password","user");
        Need need = new Need(99, "apple", 10, "fruit");
        user.addToBasket(need);
        assertEquals(need, user.getBasket().get(0));
    }

    /**
     * Tests the 'toString' method in the User class.
     * Verifies that the User is correctly converted to a string format.
     */
    @Test
    public void testToString() {
        User user = new User("Denny", "password","user");
        assertEquals(String.format(User.STRING_FORMAT, "Denny", new ArrayList<>()), user.toString());
    }

    /**
     * Tests the 'setName' method in the User class.
     * Verifies that the user's name can be updated correctly.
     */
    @Test
    public void testSetName() {
        User user = new User("Owen", "password","user");
        user.setName("Bob");
        assertEquals("Bob", user.getName());
    }

    /**
     * Tests the 'addToBasket' and 'getBasket' methods in the User class.
     * Verifies that a Need is correctly added to the basket and can be retrieved.
     */
    @Test
    public void testAddToBasketAndGetBasket() {
        User user = new User("Owen", "password","user");
        Need need = new Need(1, "Corn", 0.30, "Vegetable");
        user.addToBasket(need);
        assertEquals("Corn", user.getBasket().get(0).getName());
    }
}
