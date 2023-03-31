package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username="admin",password="password",roles={"ADMIN"})
public class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String restaurantName = "Test Restaurant";
    private String cityName = "Test City";
    private Long createdRestaurantId;

    @BeforeEach
    public void setup() throws Exception {
        // Create a new RestaurantInput object with the given information
        RestaurantInput restaurantInput = new RestaurantInput()
                .name(restaurantName)
                .city(cityName)

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

        // Parse the JSON response
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Extract the "restaurant" section
        JsonNode restaurantNode = jsonNode.get("restaurant");

        // Convert the "restaurant" JSON section to a Restaurant object
        Restaurant restaurant = objectMapper.treeToValue(restaurantNode, Restaurant.class);

        createdRestaurantId = restaurant.getId();
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (createdRestaurantId != null) {
            mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/{restaurantId}", createdRestaurantId))
                    .andExpect(status().isNoContent());
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
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
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
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

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
        Integer restaurantId = createdRestaurantId.intValue();

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

    @Test
    public void testUpdateRestaurantById() throws Exception {
        Integer restaurantId = createdRestaurantId.intValue();

        // Create a new RestaurantInput object with the updated information
        RestaurantInput updatedRestaurantInput = new RestaurantInput()
                .name("Updated Restaurant")
                .city("Updated City")

        // Convert the updated RestaurantInput object to a JSON string
        String updatedRestaurantJson = objectMapper.writeValueAsString(updatedRestaurantInput);

        // Update the restaurant using a PUT request and MockMvc
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRestaurantJson))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the JSON response
        String jsonResponse = result.getResponse().getContentAsString();
        Restaurant updatedRestaurant = objectMapper.readValue(jsonResponse, Restaurant.class);

        // Check if the restaurant was updated successfully
        Assertions.assertEquals(restaurantId.intValue(), updatedRestaurant.getId().intValue());
        Assertions.assertEquals(updatedRestaurantInput.getName(), updatedRestaurant.getName());
        Assertions.assertEquals(updatedRestaurantInput.getCity(), updatedRestaurant.getCity());
    }


    // This test should be expecting a 401 - not sure why it thinks I want a 201?
    @Test
    @WithAnonymousUser
    public void testUpdateRestaurantById_unauthorized() throws Exception {
        Integer restaurantId = createdRestaurantId.intValue();

        // Create a new RestaurantInput object with the updated information
        RestaurantInput updatedRestaurantInput = new RestaurantInput()
                .name("Updated Restaurant")
                .city("Updated City")

        // Convert the updated RestaurantInput object to a JSON string
        String updatedRestaurantJson = objectMapper.writeValueAsString(updatedRestaurantInput);

        // Attempt to update the restaurant using a PUT request and MockMvc as an anonymous user
        mockMvc.perform(MockMvcRequestBuilders.put("/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRestaurantJson))
                        .andExpect(status().isUnauthorized()); // Expect an HTTP 401 Unauthorized status
    }

}