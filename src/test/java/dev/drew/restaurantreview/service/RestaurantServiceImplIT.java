package dev.drew.restaurantreview.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import junit.framework.Assert;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.openapitools.model.User.RoleEnum.ADMIN;
import static org.openapitools.model.User.RoleEnum.REVIEWER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceImplIT {

    @Autowired
    private RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private SecurityUser createSecurityUserWithRole(User.RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(role);

        return new SecurityUser(userEntity);
    }

    @Test
    public void testGetAllRestaurants_noFilters() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");

        restaurantRepository.save(restaurantEntity);

        ResponseEntity<List<Restaurant>> response = restaurantServiceImpl.getAllRestaurants(null, null, null);

        List<Restaurant> actual = response.getBody();

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


        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        ResponseEntity<List<Restaurant>> response = restaurantServiceImpl.getAllRestaurants("City", null, null);

        List<Restaurant> actual = response.getBody();

        for (Restaurant restaurant : actual) {
            Assertions.assertEquals("City", restaurant.getCity());
        }
    }

    @Test
    public void testGetAllRestaurants_filterByRating() throws Exception {
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


        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        ResponseEntity<List<Restaurant>> response = restaurantServiceImpl.getAllRestaurants(null, 5, null);

        List<Restaurant> actual = response.getBody();

        for (Restaurant restaurant : actual) {
            Assertions.assertEquals(5, restaurant.getRating());
        }
    }

    @Test
    public void testGetAllRestaurants_filterByUserId() throws Exception {
        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(1L);
        restaurant1.setName("TestCity");
        restaurant1.setCity("City");
        restaurant1.setRating(1);
        restaurant1.setUserId(1L);

        RestaurantEntity restaurant2 = new RestaurantEntity();
        restaurant2.setId(2L);
        restaurant2.setName("TestTown");
        restaurant2.setCity("Town");
        restaurant2.setRating(5);
        restaurant2.setUserId(2L);


        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        ResponseEntity<List<Restaurant>> response = restaurantServiceImpl.getAllRestaurants(null, null, 1L);

        List<Restaurant> actual = response.getBody();

        for (Restaurant restaurant : actual) {
            Assertions.assertEquals(1L, restaurant.getUserId());
        }
    }

    @Test
    public void testGetRestaurantById() {
        // Save a sample restaurant to the repository
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");
        restaurantRepository.save(restaurantEntity);

        // Call the getRestaurantById method
        ResponseEntity<Restaurant> response = restaurantServiceImpl.getRestaurantById(1);

        // Check if the response status is OK and the restaurant details are correct
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Restaurant actualRestaurant = response.getBody();
        assertNotNull(actualRestaurant);

        // Prepare the expected restaurant data
        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId(1L);
        expectedRestaurant.setName("Test");
        expectedRestaurant.setCity("City");

        // Compare the actual and expected restaurants
        assertEquals(expectedRestaurant, actualRestaurant);
    }

    @Test
    public void testGetRestaurantById_notFound() {
        // Try to get a restaurant with an ID that doesn't exist in the repository
        ResponseEntity<Restaurant> response = restaurantServiceImpl.getRestaurantById(-1);

        // Check if the response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateRestaurantById() {

        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(REVIEWER);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Save a sample restaurant to the repository
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");
        restaurantEntity.setUserId(securityUser.getId());
        restaurantRepository.save(restaurantEntity);

        // Create an instance of RestaurantInput with updated information
        RestaurantInput restaurantInput = new RestaurantInput();
        restaurantInput.setName("Updated Test");
        restaurantInput.setCity("Updated City");

        // Call the updateRestaurantById method with the saved restaurant's ID and the RestaurantInput instance
        ResponseEntity<Restaurant> response = restaurantServiceImpl.updateRestaurantById(1, restaurantInput);

        // Check if the response status is OK and the updated restaurant details are correct
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Restaurant actualRestaurant = response.getBody();
        assertNotNull(actualRestaurant);

        // Prepare the expected restaurant data
        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId(1L);
        expectedRestaurant.setName("Updated Test");
        expectedRestaurant.setCity("Updated City");
        expectedRestaurant.setUserId(1L);

        // Compare the actual and expected restaurants
        assertEquals(expectedRestaurant, actualRestaurant);
    }

    @Test
    public void testUpdateRestaurantById_notOwner() {

        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(REVIEWER);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Save a sample restaurant to the repository
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");
        restaurantEntity.setUserId(2L);
        restaurantRepository.save(restaurantEntity);

        // Create an instance of RestaurantInput with updated information
        RestaurantInput restaurantInput = new RestaurantInput();
        restaurantInput.setName("Updated Test");
        restaurantInput.setCity("Updated City");

        // Call the updateRestaurantById method with the saved restaurant's ID and the RestaurantInput instance
        ResponseEntity<Restaurant> response = restaurantServiceImpl.updateRestaurantById(1, restaurantInput);

        // Check if the response status is FORBIDDEN
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testUpdateRestaurantById_isAdmin() {

        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Save a sample restaurant to the repository
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");
        restaurantEntity.setUserId(2L);
        restaurantRepository.save(restaurantEntity);

        // Create an instance of RestaurantInput with updated information
        RestaurantInput restaurantInput = new RestaurantInput();
        restaurantInput.setName("Updated Test");
        restaurantInput.setCity("Updated City");

        // Call the updateRestaurantById method with the saved restaurant's ID and the RestaurantInput instance
        ResponseEntity<Restaurant> response = restaurantServiceImpl.updateRestaurantById(1, restaurantInput);

        // Check if the response status is OK and the updated restaurant details are correct
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Restaurant actualRestaurant = response.getBody();
        assertNotNull(actualRestaurant);

        // Prepare the expected restaurant data
        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId(1L);
        expectedRestaurant.setName("Updated Test");
        expectedRestaurant.setCity("Updated City");
        expectedRestaurant.setUserId(2L);

        // Compare the actual and expected restaurants
        assertEquals(expectedRestaurant, actualRestaurant);
    }

    @Test
    public void testDeleteRestaurantById_isAdmin() {
        // Create SecurityUser and set it as the principal in the Authentication object
        SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Set the Authentication object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Save a sample restaurant to the repository
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setCity("City");
        restaurantEntity.setUserId(2L);
        restaurantRepository.save(restaurantEntity);

        // Call the deleteRestaurantById method with the saved restaurant's ID
        ResponseEntity<Void> response = restaurantServiceImpl.deleteRestaurantById(1);

        // Check if the response status is NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Check if the restaurant has been deleted from the repository
        assertFalse(restaurantRepository.existsById(1L));
    }

}