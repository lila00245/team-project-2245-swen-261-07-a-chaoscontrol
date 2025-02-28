package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

@ExtendWith(MockitoExtension.class)
public class NeedFileDAOTest {

    private static final String TEST_FILE = "testNeeds.json";

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private NeedFileDAO needFileDAO;

    // this method runs before each test to ensure setup functions
    // for all methods in addition to the method itself
    @BeforeEach
    void testSetUp() throws IOException {
        Need[] mockNeeds = { new Need(1, "Test Need", 50, "Food") };
        when(mockObjectMapper.readValue(any(File.class), eq(Need[].class))).thenReturn(mockNeeds);
        needFileDAO = new NeedFileDAO(TEST_FILE, mockObjectMapper);
    }

    @Test
    void testLoadOnConstruction() throws IOException {
        // we expect that the output is one Need at this point
        Need need = needFileDAO.getNeed(1);
        assertNotNull(need, "Need (1) should exist.");
        assertEquals("Test Need", need.getName());
        assertEquals(50, need.getCost());
        assertEquals("Food", need.getFoodGroup());
    }

    @Test
    void testGetNeeds() {
        Need[] allNeeds = needFileDAO.getNeeds();
        assertEquals(1, allNeeds.length, "Should GET 1 Need from mock data");
        assertEquals("Test Need", allNeeds[0].getName());
    }

    @Test
    void testFindNeeds() {
        // finds a need that contains certain text
        Need[] found = needFileDAO.findNeeds("Test");
        assertEquals(1, found.length, "Should find 1 Need with string 'Test Need'");
        Need[] notFound = needFileDAO.findNeeds("NotReal");
        assertEquals(0, notFound.length, "Should find no Needs with string 'NotReal'");
    }

    @Test
    void testGetNeed_NotFound() {
        Need need = needFileDAO.getNeed(100000);
        assertNull(need, "Should return null");
    }

    @Test
    void testCreateNeed() throws IOException {
        // Create a new Need
        Need newNeed = new Need(0, "New Need", 100, "Misc");
        Need createdNeed = needFileDAO.createNeed(newNeed);

        assertNotNull(createdNeed, "Created need should not be null");
        assertEquals(2, createdNeed.getId(), "New ID should be 2"); // should autoincrement ID
        assertEquals("New Need", createdNeed.getName());
        assertEquals(100, createdNeed.getCost());
        assertEquals("Misc", createdNeed.getFoodGroup());
        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(Need[].class)); // verify the write
    }

    @Test
    void testUpdateNeed() throws IOException {
        // updates Need (1)
        Need updatedNeed = new Need(1, "Update", 200, "UpdatedGroup");
        Need result = needFileDAO.updateNeed(updatedNeed);

        assertNotNull(result, "Updating an existing need should return the need");
        assertEquals(1, result.getId());
        assertEquals("Update", result.getName());
        assertEquals(200, result.getCost());
        assertEquals("UpdatedGroup", result.getFoodGroup());

        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(Need[].class)); // returns save once
    }

    @Test
    void testUpdateNeed_NotFound() throws IOException {
        // updates a need that isn't real, returns error
        Need nonExisting = new Need(1234, "Does not exist", 1234, "None");
        Need result = needFileDAO.updateNeed(nonExisting);
        assertNull(result, "Should return null if that Need doesn't exist");
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(Need[].class));
    }

    @Test
    void testDeleteNeed() throws IOException {
        // Need with ID=1 exists, so deleting should return true
        boolean deleted = needFileDAO.deleteNeed(1);
        assertTrue(deleted, "Deleting Need should pass");
        verify(mockObjectMapper, times(1)).writeValue(any(File.class), any(Need[].class));
        Need result = needFileDAO.getNeed(1);
        assertNull(result, "Need (1) should have deleted");
    }

    @Test
    void testDeleteNeed_NotFound() throws IOException {
        boolean deleted = needFileDAO.deleteNeed(999);
        assertFalse(deleted, "Should return False for non-existent Need deletion"); // deletes a Need that doesn't exist
        verify(mockObjectMapper, never()).writeValue(any(File.class), any(Need[].class));
    }
}