package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the User class
 * 
 * @author 
 */
@Tag("Model-tier")
public class NeedTest {

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

    @Test
    public void testSetID() {
        // Setup
        Need need = new Need(101, "Chicken Wings", 4.56, "Meat");
        int id = 201;

        // Invoke
        need.setId(id);

        // Analyze
        assertEquals(id, need.getId());
    }

    @Test
    public void testSetName() {
        // Setup
        Need need = new Need(101, "Chicken Wings", 4.56, "Meat");
        String name = "Grapes";

        // Invoke
        need.setName(name);

        // Analyze
        assertEquals(name, need.getName());
    }

    @Test
    public void testSetCost() {
        // Setup
        Need need = new Need(101, "Chicken Wings", 4.56, "Meat");
        double cost = 1.23;

        // Invoke
        need.setCost(cost);

        // Analyze
        assertEquals(cost, need.getCost());
    }

    @Test
    public void testSetFoodGroup() {
        // Setup
        Need need = new Need(101, "Chicken Wings", 4.56, "Meat");
        String foodGroup = "Fruit";

        // Invoke
        need.setFoodGroup(foodGroup);

        // Analyze
        assertEquals(foodGroup, need.getFoodGroup());
    }


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
