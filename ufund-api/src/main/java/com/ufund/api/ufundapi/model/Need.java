package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a need entity
 * 
 * @author Team 1A - ChaosControl
 */
public class Need{
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [id=%d, name=%s, cost=&.2f, foodGroup=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("cost") private double cost;
    @JsonProperty("foodGroup") private String foodGroup;

    /**
     * Create a need with the given id and name
     * @param id The id of the need
     * @param name The name of the need
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("cost") double cost,
    @JsonProperty("foodGroup") String foodGroup) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.foodGroup = foodGroup;
    }

    /**
     * Retrieves the id of the need
     * @return The id of the need
     */
    public int getId() {return id;}

    /**
     * Sets the id of the need
     * @param id The id of the need
     */
    public void setId(int id) {this.id = id;}

    /**
     * Gets the cost of the need
     * @return The cost of the need
     */
    public double getCost() {return cost;}

    /**
     * Sets the food group of the need
     * @param foodGroup The food group of the need
     */
    public void setFoodGroup(String foodGroup) {this.foodGroup = foodGroup;}

    /**
     * Sets the cost of the need
     * @param cost The cost of the need
     */
    public void setCost(double cost) {this.cost = cost;}

    /**
     * Sets the name of the need - necessary for JSON object to Java object deserialization
     * @param name The name of the need
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getName() {return name;}

    /**
     * Retrieves the food_group of the need
     * @return The food_group of the need
     */
    public String getFoodGroup() {return foodGroup;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,cost,foodGroup);
    }
}