package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Need class.
 * These tests cover the creation, getter and setter methods,
 * and the toString method of the Need class.
 * 
 * @author Ariel
 */
@Tag("Model-tier")
public class NeedTest {

    /**
     * Test the creation of a Need object and its getter methods.
     * Verifies that the correct values are returned by the getters.
     */
    @Test
    public void testCreateNeedAndGetters() {
        // Setup
        int id = 101;
        String name = "Chicken Wings";
        double cost = 4.56;
        String foodGroup = "Meat";

        // Invoke
        Need need = new Need(id, name, cost, foodGroup);

        // Analyze
        assertEquals(id, need.getId());
        assertEquals(name, need.getName());
        assertEquals(cost, need.getCost());
        assertEquals(foodGroup, need.getFoodGroup());
    }

    /**
     * Test the setter methods for all fields in the Need class.
     * Verifies that the setters correctly update the values.
     */
    @Test
    public void testSetters() {
        // Setup
        Need need = new Need(101, "Chicken Wings", 4.56, "Meat");
        int id = 201;
        String name = "Grapes";
        double cost = 1.23;
        String foodGroup = "Fruit";

        // Invoke
        need.setId(id);
        need.setName(name);
        need.setCost(cost);
        need.setFoodGroup(foodGroup);

        // Analyze
        assertEquals(id, need.getId());
        assertEquals(name, need.getName());
        assertEquals(cost, need.getCost());
        assertEquals(foodGroup, need.getFoodGroup());
    }

    /**
     * Test the toString method in the Need class.
     * Verifies that the toString method returns the correct string representation.
     */
    @Test
    public void testToString() {
        // Setup
        int id = 101;
        String name = "Chicken Wings";
        double cost = 4.56;
        String foodGroup = "Meat";
        String expected_string = String.format(Need.STRING_FORMAT, id, name, cost, foodGroup);
        Need need = new Need(id, name, cost, foodGroup);

        // Invoke
        String actual_string = need.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
