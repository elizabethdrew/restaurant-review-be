package dev.drew.restaurantreview.controller.unit;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testAddNewRestaurant() throws Exception {
        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City");
        Restaurant restaurant = new Restaurant().id(1L).name(input.getName()).city(input.getCity());

        // Mock the service call
        when(restaurantService.addNewRestaurant(any(RestaurantInput.class))).thenReturn(restaurant);

        // Call the endpoint and check the response status
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant().id(1L).name("Restaurant 1").city("City 1"),
                new Restaurant().id(2L).name("Restaurant 2").city("City 2")
        );

        Pageable pageable = PageRequest.of(0, 20); // Corresponds to the default size we set
        when(restaurantService.getAllRestaurantsV1(null, null, null, null, null, false, pageable)).thenReturn(restaurants);

        mockMvc.perform(get("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRestaurantById() throws Exception {
        int restaurantId = 1;
        Restaurant restaurant = new Restaurant().id((long) restaurantId).name("Test Restaurant").city("Test City");

        when(restaurantService.getRestaurantById(eq(restaurantId))).thenReturn(restaurant);

        mockMvc.perform(get("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateRestaurantById() throws Exception {
        int restaurantId = 1;
        RestaurantInput input = new RestaurantInput().name("Updated Restaurant").city("Updated City");
        Restaurant updatedRestaurant = new Restaurant().id((long) restaurantId).name(input.getName()).city(input.getCity());

        when(restaurantService.updateRestaurantById(eq(restaurantId), eq(input))).thenReturn(updatedRestaurant);

        mockMvc

                .perform(put("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteRestaurantById() throws Exception {
        int restaurantId = 1;

        mockMvc.perform(delete("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
