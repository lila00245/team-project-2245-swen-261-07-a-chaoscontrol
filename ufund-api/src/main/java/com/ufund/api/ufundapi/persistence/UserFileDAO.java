package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Team 1A - ChaosControl
 */
@Component
public class UserFileDAO implements UserDAO {
    Map<String,User> users;   // Provides a local cache of the User objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the users from the file
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * <br>
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() { 
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            userArrayList.add(user);
        }

        return userArrayList.toArray(User[]::new);
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User user} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each User to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getName(),user);
        }
        // Make the next id one greater than the maximum from the file
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User getUser(String name) {
        synchronized(users) {
            if (users.containsKey(name)){
                return users.get(name);
            }else{
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(user) {
            // We create a new User object because the id field is immutable
            // and we use to assign the next unique id
            if(getUser(user.getName())==null){
                User newUser = new User(user.getName());
                users.put(newUser.getName(),newUser);
                save(); // may throw an IOException
                return newUser;
            } return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if (users.containsKey(user.getName()) == false){
                return null;  // user does not exist
            }
            users.put(user.getName(),user);
            save(); // may throw an IOException
            return user;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(String name) throws IOException {
        synchronized(users) {
            if (users.containsKey(name)) {
                users.remove(name);
                return save();
            }
            else
                return false;
        }
    }
}