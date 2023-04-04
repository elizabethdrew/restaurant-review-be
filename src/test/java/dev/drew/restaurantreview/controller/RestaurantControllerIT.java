package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.openapitools.model.User.RoleEnum.ADMIN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username="admin",password="password",roles={"ADMIN"})
public class RestaurantControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private RestaurantRepository restaurantRepository;

    public RestaurantControllerIT() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    private SecurityUser createSecurityUserWithRole(User.RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(role);

        return new SecurityUser(userEntity);
    }

    @Test
    public void testGetAllRestaurants_noFilters() throws Exception {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");


        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        restaurantEntities.add(restaurantEntity);

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        List<Restaurant> actual = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        List<Restaurant> expected = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.id(1L);
        restaurant.setName("Test");
        restaurant.city("City");
        expected.add(restaurant);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllRestaurants_filterByCity() throws Exception {
        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(1L);
        restaurant1.setName("TestCity");
        restaurant1.setCity("City");

        RestaurantEntity restaurant2 = new RestaurantEntity();
        restaurant2.setId(2L);
        restaurant2.setName("TestTown");
        restaurant2.setCity("Town");


        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        restaurantEntities.add(restaurant1);
        restaurantEntities.add(restaurant2);

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("city", "City"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        for (Restaurant restaurant : restaurants) {
            Assertions.assertEquals("City", restaurant.getCity());
        }
    }

    @Test
    public void testGetAllRestaurants_filterByRating() throws Exception {

        Integer testRating = 5;

        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(1L);
        restaurant1.setName("TestCity");
        restaurant1.setCity("City");
        restaurant1.setRating(1);

        RestaurantEntity restaurant2 = new RestaurantEntity();
        restaurant2.setId(2L);
        restaurant2.setName("TestTown");
        restaurant2.setCity("Town");
        restaurant2.setRating(5);


        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        restaurantEntities.add(restaurant1);
        restaurantEntities.add(restaurant2);

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("rating", testRating.toString()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        for (Restaurant restaurant : restaurants) {
            Assertions.assertEquals(testRating, restaurant.getRating());
        }
    }

    @Test
    public void testGetAllRestaurants_filterByUserId() throws Exception {
        Long testUserId = 1L;

        RestaurantEntity restaurant1 = new RestaurantEntity();
            restaurant1.setId(1L);
            restaurant1.setName("TestCity");
            restaurant1.setCity("City");
            restaurant1.setUserId(1L);

        RestaurantEntity restaurant2 = new RestaurantEntity();
            restaurant2.setId(2L);
            restaurant2.setName("TestTown");
            restaurant2.setCity("Town");
            restaurant2.setUserId(2L);


        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
            restaurantEntities.add(restaurant1);
            restaurantEntities.add(restaurant2);

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("user_id", testUserId.toString()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Restaurant> restaurants = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            for (Restaurant restaurant : restaurants) {
            Assertions.assertEquals(testUserId, restaurant.getUserId());
        }
}
//

    @Test
    public void testGetRestaurantById_exists() throws Exception {
        Long restaurantId = 1L;

        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(restaurantId);
        restaurant1.setName("TestCity");
        restaurant1.setCity("City");

        Optional<RestaurantEntity> optionalRestaurant = Optional.of(restaurant1);

        when(restaurantRepository.findById(restaurantId)).thenReturn(optionalRestaurant);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(jsonResponse, Restaurant.class);
        assertEquals(restaurantId, restaurant.getId());
    }

    @Test
    public void testGetRestaurantById_notFound() throws Exception {
        Long restaurantId = -1L; // Use an ID that doesn't exist in the database

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateRestaurantById() throws Exception {
        Long restaurantId = 1L;
        Long userId = 1L;

        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(restaurantId);
        restaurant1.setName("TestCity");
        restaurant1.setCity("City");
        restaurant1.setUserId(userId);

        Optional<RestaurantEntity> optionalRestaurant = Optional.of(restaurant1);

        when(restaurantRepository.findById(restaurantId)).thenReturn(optionalRestaurant);

        // Mock the save method
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Create a new RestaurantInput object with the updated information
        RestaurantInput updatedRestaurantInput = new RestaurantInput()
                .name("Updated Restaurant")
                .city("Updated City");

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

    @Test
    public void testAddNewRestaurant_authorized() throws Exception {

        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create new restaurant input object
        RestaurantInput input = new RestaurantInput()
                .name("New Restaurant")
                .city("New City");

        String inputJson = objectMapper.writeValueAsString(input);

        when(restaurantRepository.save(any(RestaurantEntity.class))).thenAnswer(invocation -> {
            RestaurantEntity restaurant = invocation.getArgument(0);
            restaurant.setId(1L);
            return restaurant;
        });

        // Add the new restaurant using a POST request and MockMvc
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Parse the JSON response
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode createdRestaurant = rootNode.path("restaurant");

        // Check if the restaurant was added successfully
        if (result.getResponse().getStatus() != HttpStatus.CREATED.value()) {
            throw new RuntimeException("Failed to add test restaurant.");
        }

        // Extract user_id from the response
        Long userIdResponse = createdRestaurant.path("user_id").asLong();

        // Check if the current user's id has been added to the created restaurant
        Assertions.assertEquals(securityUser.getId(), userIdResponse);
    }

    @Test
    @WithAnonymousUser
    public void testAddNewRestaurant_unauthorized() throws Exception {

        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City");
        String inputJson = objectMapper.writeValueAsString(input);

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteRestaurantById() throws Exception {
        Long restaurantId = 1L;
        Long userId = 1L;

        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(restaurantId);
        restaurant1.setName("TestCity");
        restaurant1.setCity("City");
        restaurant1.setUserId(userId);

        Optional<RestaurantEntity> optionalRestaurant = Optional.of(restaurant1);

        when(restaurantRepository.findById(restaurantId)).thenReturn(optionalRestaurant);

        // Delete the restaurant using a DELETE request and MockMvc
        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isNoContent());

        // Verify that the delete method was called with the correct id
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }
}
