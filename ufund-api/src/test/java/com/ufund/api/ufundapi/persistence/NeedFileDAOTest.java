package com.ufund.api.ufundapi.persistence;
import com.ufund.api.ufundapi.model.Need;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NeedFileDAOTest {

    private static final String TEST_FILE = "testNeeds.json";

    @Mock
    private ObjectMapper mockObjectMapper;
    private NeedFileDAO needFileDAO;

    // this method runs before each test to ensure setup functions
    // for all methods in addition to the method itself
    @BeforeEach
    void testSetUp() throws IOException {
        Need[] mockNeeds = { new Need(1, "Test Need", 50, "Food") };
        when(mockObjectMapper.readValue(any(File.class), eq(Need[].class)))
            .thenReturn(mockNeeds);
        // manually create the NeedFileDAO to avoid NPE
        needFileDAO = new NeedFileDAO(TEST_FILE, mockObjectMapper);
    }

    @Test
    void testLoadOnConstruction() throws IOException {
        Need need = needFileDAO.getNeed(1);
        assertNotNull(need, "Need (1) should exist");
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
        Need[] found = needFileDAO.findNeeds("Test");
        assertEquals(1, found.length, "Should find 1 Need with string 'Test Need'");
        Need[] notFound = needFileDAO.findNeeds("NotReal");
        assertEquals(0, notFound.length, "Should find no Needs with string 'NotReal'");
    }

    @Test
    void testCreateNeed() throws IOException {
        Need newNeed = new Need(0, "New Need", 100, "Misc");
        Need createdNeed = needFileDAO.createNeed(newNeed);
        assertNotNull(createdNeed, "Created need should not be null");
        assertEquals(2, createdNeed.getId(), "New ID should be 2");
        assertEquals("New Need", createdNeed.getName());
        assertEquals(100, createdNeed.getCost());
        assertEquals("Misc", createdNeed.getFoodGroup());
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(Need[].class));
    }

    @Test
    void testUpdateNeed() throws IOException {
        Need updatedNeed = new Need(1, "Update", 200, "UpdatedGroup");
        Need result = needFileDAO.updateNeed(updatedNeed);
        assertNotNull(result, "Updating an existing need should return the need");
        assertEquals(1, result.getId());
        assertEquals("Update", result.getName());
        assertEquals(200, result.getCost());
        assertEquals("UpdatedGroup", result.getFoodGroup());
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(Need[].class));
    }

    @Test
    void testDeleteNeed() throws IOException {
        boolean deleted = needFileDAO.deleteNeed(1);
        assertTrue(deleted, "Deleting Need should pass");
        verify(mockObjectMapper, times(1))
            .writeValue(any(File.class), any(Need[].class));
        Need result = needFileDAO.getNeed(1);
        assertNull(result, "Need (1) should have deleted");
    }
}
