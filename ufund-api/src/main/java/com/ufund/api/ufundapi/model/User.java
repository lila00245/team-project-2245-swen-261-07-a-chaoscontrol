package com.ufund.api.ufundapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user entity
 * 
 * @author Team 1A - ChaosControl
 */
public class User{

    // Package private for tests
    static final String STRING_FORMAT = "User [name=%s, basket=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("password") private String password;  // for storing password for authentication
    @JsonProperty("basket") private ArrayList<Need> basket;
    @JsonProperty("role") private String role;  // role (user or admin)

    /**
     * Create a user with the given name, password, basket, and role
     * @param name The name of the user
     * @param password The password of the user
     * @param basket The basket of the user
     * @param role The role of the user
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("name") String name, 
                @JsonProperty("password") String password,
                @JsonProperty("basket") ArrayList<Need> basket,
                @JsonProperty("role") String role) {
                        
        this.name = name;
        this.password = password;
        this.basket = basket;
        this.role = role;
    }

    /**
     * Create a user with the given name and password
     * basket is initialized to empty list
     * role is set to "user"
     * @param name The name of the user
     * @param password The password of the user
     */
    public User(String name, String password, String role){

        this.name = name;
        this.password = password;
        this.basket = new ArrayList<>();
        this.role = role;
    }
    
    /**
     * Sets the name of the user 
     * @param name The name of the user
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the user
     * @return The name of the user
     */
    public String getName() {return name;}

    /**
     * Retrieves the password of the user
     * @return The password of the user
     */
    public String getPassword() {return password;}

    /**
     * Sets the password of the user 
     * @param password The password of the user
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Retrieves the role of the user
     * @return The role of the user
     */
    public String getRole() {return role;}
    
    /**
     * Sets the role of the user 
     * @param role The role of the user
     */
    public void setRole(String role) {this.role = role;}

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