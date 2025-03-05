package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user entity
 * 
 * @author Team 1A - ChaosControl
 */
public class User{
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [name=%s, basket=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("basket") private ArrayList<Need> basket;

    /**
     * Create a user with the given name
     * @param name The name of the user
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("name") String name, @JsonProperty("basket") ArrayList<Need> basket) {
        this.name = name;
        this.basket = basket;
    }

    /**
     * Create a user with the given name and an empty basket
     * @param name The name of the user
     */
    public User(String name){
        this.name = name;
        this.basket = new ArrayList<>();
    }

    /**
     * Sets the name of the user - necessary for JSON object to Java object deserialization
     * @param name The name of the user
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the user
     * @return The name of the user
     */
    public String getName() {return name;}

    /**
     * Retrieves the basket of the user
     * @return the basket of the user
     */
    public ArrayList<Need> getBasket(){return basket;}

    /**
     * Adds a need to the basket
     * @param need The need to add to the basket
     */
    public void addToBasket(Need need){basket.add(need);}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,name,basket);
    }
}