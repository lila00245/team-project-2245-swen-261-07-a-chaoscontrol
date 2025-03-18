package com.ufund.api.ufundapi.persistence;

import com.ufund.api.ufundapi.model.Need;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the Need File DAO class.
 * 
 * @author Vlad
 */
@Tag("Persistence-Tier")
@ExtendWith(MockitoExtension.class)
public class NeedFileDAOTest {

    private static final String TEST_FILE = "testNeeds.json";

    @Mock
    private ObjectMapper mockObjectMapper;
    private NeedFileDAO needFileDAO;

    /**
     * Sets up the test environment by mocking the necessary dependencies 
     * and initializing the NeedFileDAO instance before each test.
     *
     * @throws IOException if there is an issue reading or writing to the file.
     */
    @BeforeEach
    void testSetUp() throws IOException {
        Need[] mockNeeds = { new Need(1, "Test Need", 50, "Food") };
        when(mockObjectMapper.readValue(any(File.class), eq(Need[].class)))
            .thenReturn(mockNeeds);
        needFileDAO = new NeedFileDAO(TEST_FILE, mockObjectMapper);
    }

    /**
     * Tests that the NeedFileDAO correctly loads data from the file on construction.
     * Verifies that the loaded Need has the expected attributes.
     *
     * @throws IOException if there is an issue loading the data.
     */
    @Test
    void testLoadOnConstruction() throws IOException {
        Need need = needFileDAO.getNeed(1);
        assertNotNull(need, "Need (1) should exist");
        assertEquals("Test Need", need.getName());
        assertEquals(50, need.getCost());
        assertEquals("Food", need.getFoodGroup());
    }

    /**
     * Tests the 'getNeeds' method of the NeedFileDAO.
     * Verifies that it retrieves all available 'Need' entities from the file.
     */
    @Test
    void testGetNeeds() {
        Need[] allNeeds = needFileDAO.getNeeds();
        assertEquals(1, allNeeds.length, "Should GET 1 Need from mock data");
        assertEquals("Test Need", allNeeds[0].getName());
    }

    /**
     * Tests the 'findNeeds' method of the NeedFileDAO.
     * Verifies that it correctly finds needs based on a search string.
     */
    @Test
    void testFindNeeds() {
        Need[] found = needFileDAO.findNeeds("Test");
        assertEquals(1, found.length, "Should find 1 Need with string 'Test Need'");
        Need[] notFound = needFileDAO.findNeeds("NotReal");
        assertEquals(0, notFound.length, "Should find no Needs with string 'NotReal'");
    }

    /**
     * Tests the 'createNeed' method of the NeedFileDAO.
     * Verifies that it successfully creates a new 'Need' and returns it with the correct ID and values.
     * 
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testCreateNeed() throws IOException {
        Need createdNeed = needFileDAO.createNeed("New Need","Misc", 100);
        assertNotNull(createdNeed, "Created need should not be null");
        assertEquals(2, createdNeed.getId(), "New ID should be 2");
        assertEquals("New Need", createdNeed.getName());
        assertEquals(100, createdNeed.getCost());
        assertEquals("Misc", createdNeed.getFoodGroup());
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(Need[].class));
    }

    /**
     * Tests the 'updateNeed' method of the NeedFileDAO.
     * Verifies that it correctly updates an existing 'Need' and returns the updated entity.
     * 
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testUpdateNeed() throws IOException {
        Need result = needFileDAO.updateNeed(1,"Update","UpdatedGroup",200);
        assertNotNull(result, "Updating an existing need should return the need");
        assertEquals(1, result.getId());
        assertEquals("Update", result.getName());
        assertEquals(200, result.getCost());
        assertEquals("UpdatedGroup", result.getFoodGroup());
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(Need[].class));
    }

    /**
     * Tests the 'updateNeed' method of the NeedFileDAO when a non-existent 'Need' is being updated.
     * Verifies that it returns null and does not attempt to write to the file.
     * 
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testUpdateNeedNotFound() throws IOException {
        Need result = needFileDAO.updateNeed(2,"Update","UpdatedGroup", 200);
        assertNull(result, "Updating a non-existent need should return null");
        verify(mockObjectMapper, times(0))
            .writeValue(any(File.class), any(Need[].class));
    }

    /**
     * Tests the 'deleteNeed' method of the NeedFileDAO.
     * Verifies that it correctly deletes an existing 'Need' and returns true.
     * 
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testDeleteNeed() throws IOException {
        boolean deleted = needFileDAO.deleteNeed(1);
        assertTrue(deleted, "Deleting Need should pass");
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(Need[].class));
        Need result = needFileDAO.getNeed(1);
        assertNull(result, "Need (1) should have deleted");
    }

    /**
     * Tests the 'deleteNeed' method of the NeedFileDAO when trying to delete a non-existent 'Need'.
     * Verifies that it returns false and does not attempt to write to the file.
     * 
     * @throws IOException if there is an issue writing the data to the file.
     */
    @Test
    void testDeleteNeedNotFound() throws IOException {
        boolean deleted = needFileDAO.deleteNeed(2);
        assertFalse(deleted, "Deleting non-existent Need should return false");
        verify(mockObjectMapper, times(0))
            .writeValue(any(File.class), any(Need[].class));
    }
}
