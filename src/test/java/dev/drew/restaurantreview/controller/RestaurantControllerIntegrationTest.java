package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetRestaurantById_exists() throws Exception {
        Integer restaurantId = 17;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(jsonResponse, Restaurant.class);
        assertEquals((long)restaurantId, (long)restaurant.getId());
    }

    @Test
    public void testGetRestaurantById_notFound() throws Exception {
        Integer restaurantId = -1; // Use an ID that doesn't exist in the database

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isNotFound());
    }
}