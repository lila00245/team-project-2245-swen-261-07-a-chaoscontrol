package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the NeedController class
 * 
 * @author Lila
 */
@Tag("Controller-Tier")
public class NeedControllerTest {
    private NeedDAO mockNeedDao;
    private NeedController needController;

    /**
     * Before each test, create a new NeedController object
     * and inject a mock NeedDAO object
     */
    @BeforeEach
    public void setupNeedController() {
        mockNeedDao = mock(NeedDAO.class);
        needController = new NeedController(mockNeedDao);
    }

    @Test
    public void testGetNeed() throws IOException {
        // setup
        Need need = new Need(99, "apple", 10, "fruit");
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockNeedDao.getNeed(1)).thenReturn(need);

        // invoke
        ResponseEntity<Need> response = needController.getNeed(1);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws IOException {
        // setup
        int needID = 99;
        // When the same id is passed in, our mock Need DAO will return null
        when(mockNeedDao.getNeed(needID)).thenReturn(null);

        // invoke
        ResponseEntity<Need> response = needController.getNeed(1);

        // analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception {
        // setup
        int needID = 99;
        // when getHero is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDao).getNeed(needID);

        //invoke
        ResponseEntity<Need> response = needController.getNeed(needID);
    
        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateNeed() throws IOException {
        // set up 
        Need need = new Need(99, "apple", 10, "fruit");
        // when creatNeed is called, return true simulating succesful creation
        // and save
        when(mockNeedDao.createNeed(need)).thenReturn(need);

        // invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testCreateNeedFailed() throws IOException {
        // set up 
        Need need = new Need(99, "apple", 10, "fruit");
        // when creatNeed is called, return null simulating failed creation
        when(mockNeedDao.createNeed(need)).thenReturn(null);

        // invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateNeedHandleException() throws Exception {
        // set up 
        Need need = new Need(99, "apple", 10, "fruit");
        // when creatNeed is called, throw an IOException
        doThrow(new IOException()).when(mockNeedDao).createNeed(need);

        // invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateNeed() throws IOException {
        // setup 
        Need need = new Need(99, "apple", 10, "fruit");
        // when updateNeed is called, return true simulating succesful update
        // and save
        when(mockNeedDao.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = needController.updateNeed(need);
        need.setName("banana");

        //invoke
        response = needController.updateNeed(need);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testUpdateNeedNotFound() throws IOException {
        // setup 
        Need need = new Need(99, "apple", 10, "fruit");
        // when updateNeed is called, return null simulating failed update
        when(mockNeedDao.updateNeed(need)).thenReturn(null);

        //invoke
        ResponseEntity<Need> response = needController.updateNeed(need);

        // analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateNeedHandleException() throws Exception {
        // setup 
        Need need = new Need(99, "apple", 10, "fruit");
        // when updateNeed is called, throw an IOException
        doThrow(new IOException()).when(mockNeedDao).updateNeed(need);

        //invoke
        ResponseEntity<Need> response = needController.updateNeed(need);

        // analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetNeeds() throws IOException { // works on any size from 0
        // setup
        Need[] needs = new Need[2];
        needs[0] = new Need(99, "apple", 10, "fruit");
        needs[1] = new Need(100, "banana", 5, "fruit");
        // When getNeeds is called on the Mock Need DAO, return the array of Needs
        when(mockNeedDao.getNeeds()).thenReturn(needs);

        // invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    @Test
    public void testGetNeesdHandleException() throws Exception {
        // setup
        // when getNeeds is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDao).getNeeds();
    
        // invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchNeeds() throws IOException {
        // setup
        String searchString = "a";
        Need[] needs = new Need[2];
        needs[0] = new Need(99, "apple", 10, "fruit");
        needs[1] = new Need(100, "banana", 5, "fruit");
        // When findNeeds is called on the Mock Need DAO, return the array of Needs
        when(mockNeedDao.findNeeds(searchString)).thenReturn(needs);

        // invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws Exception {
        // setup
        String searchString = "a";
        // when findNeeds is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDao).findNeeds(searchString);
    
        // invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);

        // analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed() throws IOException {
        // setup
        int needID = 99;
        // When deleteNeed is called, return true simulating succesful deletion
        when(mockNeedDao.deleteNeed(needID)).thenReturn(true);

        // invoke
        ResponseEntity<Need> response = needController.deleteNeed(needID);

        // analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException {
        // setup
        int needID = 99;
        // When deleteNeed is called, return false simulating failed deletion
        when(mockNeedDao.deleteNeed(needID)).thenReturn(false);

        // invoke
        ResponseEntity<Need> response = needController.deleteNeed(needID);

        // analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteNeedHandleException() throws Exception {
        // setup
        int needID = 99;
        // when deleteNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDao).deleteNeed(needID);

        //invoke
        ResponseEntity<Need> response = needController.deleteNeed(needID);
    
        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}