package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the User class
 * 
 * @author SWEN Faculty
 */

@Tag("Model-tier")
public class UserTest {

    @Test
    public void testName() {
        // Setup
        String expected = "New username";
        User user = new User(expected);

        // Analyze
        assertEquals(expected,user.getName());
    }

    @Test
    public void testToString() {
        // Setup
        String name = "Owen";
        ArrayList<String> l = new ArrayList<>();
        String expected_string = String.format(User.STRING_FORMAT,name,l);
        User user = new User(name);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

    @Test
    public void testSetName(){

        //Setup
        String name = "Owen";
        User user = new User(name);

        //Analyze
        user.setName("Bob");
        assertEquals("Bob", user.getName());
    }

    @Test
    public void testAddToBasketAndGetBasket(){
        String name = "Owen";
        User user = new User(name);

        Need need = new Need(1,"Corn",.30,"Vegetable");
        user.addToBasket(need);
        assertEquals("Corn",user.getBasket().get(0).getName());
    }
}