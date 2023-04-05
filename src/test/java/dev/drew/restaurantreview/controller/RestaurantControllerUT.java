package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.controller.RestaurantController;
import dev.drew.restaurantreview.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerUT {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    void testAddNewRestaurant() throws Exception {
        RestaurantResponse restaurantResponse = new RestaurantResponse();

        // Prepare input data and expected response
        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City");
        Restaurant restaurant = new Restaurant().id(1L).name(input.getName()).city(input.getCity());
        ResponseEntity<RestaurantResponse> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restaurantResponse);

        // Mock the service call
        when(restaurantService.addNewRestaurant(any(RestaurantInput.class))).thenReturn(responseEntity);

        // Call the endpoint and check the response status
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant().id(1L).name("Restaurant 1").city("City 1");
        Restaurant restaurant2 = new Restaurant().id(2L).name("Restaurant 2").city("City 2");
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        ResponseEntity<List<Restaurant>> responseEntity = ResponseEntity.ok(restaurants);

        when(restaurantService.getAllRestaurants(null, null, null)).thenReturn(responseEntity);

        mockMvc.perform(get("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRestaurantById() throws Exception {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant().id(restaurantId).name("Test Restaurant").city("Test City");

        ResponseEntity<Restaurant> responseEntity = ResponseEntity.ok(restaurant);

        when(restaurantService.getRestaurantById(restaurantId.intValue())).thenReturn(responseEntity);

        mockMvc.perform(get("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateRestaurantById() throws Exception {
        Long restaurantId = 1L;
        RestaurantInput input = new RestaurantInput().name("Updated Restaurant").city("Updated City");
        Restaurant updatedRestaurant = new Restaurant().id(restaurantId).name(input.getName()).city(input.getCity());

        ResponseEntity<Restaurant> responseEntity = ResponseEntity.ok(updatedRestaurant);

        when(restaurantService.updateRestaurantById(restaurantId.intValue(), input)).thenReturn(responseEntity);

        mockMvc.perform(put("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteRestaurantById() throws Exception {
        Long restaurantId = 1L;

        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

        when(restaurantService.deleteRestaurantById(restaurantId.intValue())).thenReturn(responseEntity);

        mockMvc.perform(delete("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}