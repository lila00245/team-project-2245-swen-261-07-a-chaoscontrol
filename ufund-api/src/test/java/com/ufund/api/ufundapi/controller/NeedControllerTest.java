package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for the NeedController class.
 * This class tests various endpoints for CRUD operations (Create, Read, Update, Delete)
 * related to the 'Need' entity, ensuring correct HTTP status codes and response bodies.
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

    /**
     * Tests the 'getNeed' method in NeedController.
     * Verifies that a valid Need ID returns the correct Need object and an HTTP 200 OK response.
     */
    @Test
    public void testGetNeed() throws IOException {
        Need need = new Need(99, "apple", 10, "fruit");
        when(mockNeedDao.getNeed(99)).thenReturn(need);
        ResponseEntity<Need> response = needController.getNeed(99);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * Tests the 'getNeed' method in NeedController.
     * Verifies that an invalid Need ID returns an HTTP 404 NOT FOUND response.
     */
    @Test
    public void testGetNeedNotFound() throws IOException {
        when(mockNeedDao.getNeed(99)).thenReturn(null);
        ResponseEntity<Need> response = needController.getNeed(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'getNeed' method in NeedController.
     * Verifies that when an exception is thrown, an HTTP 500 INTERNAL SERVER ERROR is returned.
     */
    @Test
    public void testGetNeedHandleException() throws Exception {
        doThrow(new IOException()).when(mockNeedDao).getNeed(99);
        ResponseEntity<Need> response = needController.getNeed(99);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'createNeed' method in NeedController.
     * Verifies that a valid Need object is created and an HTTP 201 CREATED response is returned.
     */
    @Test
    public void testCreateNeed() throws IOException {
        Need need = new Need(99, "apple", 10, "fruit");
        when(mockNeedDao.createNeed("apple","fruit",10)).thenReturn(need);
        Map<String, Object> m = Map.ofEntries(
            Map.entry("name", "apple"),
            Map.entry("foodGroup", "fruit"),
            Map.entry("price", "10")
        );
        ResponseEntity<Need> response = needController.createNeed(m);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * Tests the 'createNeed' method in NeedController.
     * Verifies that if the creation fails, an HTTP 409 CONFLICT response is returned.
     */
    @Test
    public void testCreateNeedFailed() throws IOException {
        when(mockNeedDao.createNeed("apple","fruit",10)).thenReturn(null);
        Map<String, Object> m = Map.ofEntries(
            Map.entry("name", "apple"),
            Map.entry("foodGroup", "fruit"),
            Map.entry("price", "10")
        );
        ResponseEntity<Need> response = needController.createNeed(m);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Tests the 'createNeed' method in NeedController.
     * Verifies that when an exception is thrown, an HTTP 500 INTERNAL SERVER ERROR is returned.
     */
    @Test
    public void testCreateNeedHandleException() throws Exception {
        doThrow(new IOException()).when(mockNeedDao).createNeed("apple","fruit",10);
        Map<String, Object> m = Map.ofEntries(
            Map.entry("name", "apple"),
            Map.entry("foodGroup", "fruit"),
            Map.entry("price", "10")
        );
        ResponseEntity<Need> response = needController.createNeed(m);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'updateNeed' method in NeedController.
     * Verifies that when a valid Need object is updated, an HTTP 200 OK response is returned.
     */
    @Test
    public void testUpdateNeed() throws IOException {
        Need need = new Need(99, "apple", 10, "fruit");
        when(mockNeedDao.updateNeed(99,"apple",10,"fruit")).thenReturn(need);
        Map<String, Object> m = Map.ofEntries(
            Map.entry("name", "apple"),
            Map.entry("foodGroup", "fruit"),
            Map.entry("price", "10")
        );
        ResponseEntity<Need> response = needController.updateNeed(m);
        need.setName("banana");
        response = needController.updateNeed(m);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    /**
     * Tests the 'updateNeed' method in NeedController.
     * Verifies that when a valid Need object is not found, an HTTP 404 NOT FOUND response is returned.
     */
    @Test
    public void testUpdateNeedNotFound() throws IOException {
        Map<String, Object> m = Map.ofEntries(
            Map.entry("name", "apple"),
            Map.entry("foodGroup", "fruit"),
            Map.entry("price", "10")
        );
        when(mockNeedDao.updateNeed(99,"apple",10,"fruit")).thenReturn(null);
        ResponseEntity<Need> response = needController.updateNeed(m);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'updateNeed' method in NeedController.
     * Verifies that when an exception is thrown, an HTTP 500 INTERNAL SERVER ERROR is returned.
     */
    @Test
    public void testUpdateNeedHandleException() throws Exception {
        Map<String, Object> m = Map.ofEntries(
            Map.entry("name", "apple"),
            Map.entry("foodGroup", "fruit"),
            Map.entry("price", "10")
        );
        doThrow(new IOException()).when(mockNeedDao).updateNeed(99,"apple",10,"fruit");
        ResponseEntity<Need> response = needController.updateNeed(m);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'getNeeds' method in NeedController.
     * Verifies that when multiple Needs are available, an HTTP 200 OK response is returned with the list.
     */
    @Test
    public void testGetNeeds() throws IOException {
        Need[] needs = new Need[2];
        needs[0] = new Need(99, "apple", 10, "fruit");
        needs[1] = new Need(100, "banana", 5, "fruit");
        when(mockNeedDao.getNeeds()).thenReturn(needs);
        ResponseEntity<Need[]> response = needController.getNeeds();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    /**
     * Tests the 'getNeeds' method in NeedController.
     * Verifies that when an exception is thrown, an HTTP 500 INTERNAL SERVER ERROR is returned.
     */
    @Test
    public void testGetNeedsHandleException() throws Exception {
        doThrow(new IOException()).when(mockNeedDao).getNeeds();
        ResponseEntity<Need[]> response = needController.getNeeds();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'searchNeeds' method in NeedController.
     * Verifies that when a valid search string is provided, the corresponding Needs are returned with an HTTP 200 OK response.
     */
    @Test
    public void testSearchNeeds() throws IOException {
        String searchString = "a";
        Need[] needs = new Need[2];
        needs[0] = new Need(99, "apple", 10, "fruit");
        needs[1] = new Need(100, "banana", 5, "fruit");
        when(mockNeedDao.findNeeds(searchString)).thenReturn(needs);

        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    /**
     * Tests the 'searchNeeds' method in NeedController.
     * Verifies that when an exception is thrown, an HTTP 500 INTERNAL SERVER ERROR is returned.
     */
    @Test
    public void testSearchNeedsHandleException() throws Exception {
        String searchString = "a";
        doThrow(new IOException()).when(mockNeedDao).findNeeds(searchString);
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the 'deleteNeed' method in NeedController.
     * Verifies that when a valid Need ID is provided, an HTTP 200 OK response is returned.
     */
    @Test
    public void testDeleteNeed() throws IOException {
        when(mockNeedDao.deleteNeed(99)).thenReturn(true);
        ResponseEntity<Need> response = needController.deleteNeed(99);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests the 'deleteNeed' method in NeedController.
     * Verifies that when an invalid Need ID is provided, an HTTP 404 NOT FOUND response is returned.
     */
    @Test
    public void testDeleteNeedNotFound() throws IOException {
        when(mockNeedDao.deleteNeed(99)).thenReturn(false);
        ResponseEntity<Need> response = needController.deleteNeed(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the 'deleteNeed' method in NeedController.
     * Verifies that when an exception is thrown, an HTTP 500 INTERNAL SERVER ERROR is returned.
     */
    @Test
    public void testDeleteNeedHandleException() throws Exception {
        doThrow(new IOException()).when(mockNeedDao).deleteNeed(99);
        ResponseEntity<Need> response = needController.deleteNeed(99);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
