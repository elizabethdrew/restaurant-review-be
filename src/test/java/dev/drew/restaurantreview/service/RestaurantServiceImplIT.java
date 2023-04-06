package dev.drew.restaurantreview.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import org.junit.jupiter.api.*;
import org.openapitools.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
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

}