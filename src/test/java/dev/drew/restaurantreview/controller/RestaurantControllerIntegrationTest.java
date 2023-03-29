package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import org.junit.jupiter.api.Assertions;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String cityName = "Test City";
    Integer ratingNumber = 4;

    @BeforeEach
    public void setup() throws Exception {
        // Create a new RestaurantInput object with the given information
        RestaurantInput restaurantInput = new RestaurantInput()
                .name("Test Restaurant")
                .city(cityName)
                .rating(ratingNumber);

        // Convert the RestaurantInput object to a JSON string
        String restaurantInputJson = objectMapper.writeValueAsString(restaurantInput);

        // Add the new restaurant using a POST request and MockMvc
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantInputJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Check if the restaurant was added successfully
        if (result.getResponse().getStatus() != HttpStatus.CREATED.value()) {
            throw new RuntimeException("Failed to add test restaurant.");
        }
    }

    @Test
    public void testGetAllRestaurants_noFilters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllRestaurants_filterByCity() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("city", cityName))
                        .andExpect(status().isOk())
                        .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<List<Restaurant>>() {});

        for (Restaurant restaurant : restaurants) {
            Assertions.assertEquals(cityName, restaurant.getCity());
        }
    }

    @Test
    public void testGetAllRestaurants_filterByRating() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("rating", ratingNumber.toString()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<List<Restaurant>>() {});

        for (Restaurant restaurant : restaurants) {
            Assertions.assertEquals(ratingNumber, restaurant.getRating());
        }
    }

//    @Test
//    public void testGetAllRestaurants_filterByUserId() throws Exception {
//        Long userId = 1L;
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
//                        .param("user_id", userId.toString()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<List<Restaurant>>() {});
//
//        for (Restaurant restaurant : restaurants) {
//            Assertions.assertEquals(userId, restaurant.getUserId());
//        }
//    }

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