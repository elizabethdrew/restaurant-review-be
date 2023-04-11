package dev.drew.restaurantreview.service;

import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

// RestaurantService is an interface for managing restaurants.
public interface RestaurantService {

    // Add a new restaurant
    ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput);

    // Get all restaurants with optional filters (city, rating, and userId)
    ResponseEntity<List<Restaurant>> getAllRestaurants(String city, Integer rating, Long userId);

    // Get a restaurant by its ID
    ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId);

    // Update a restaurant by its ID
    ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput);

    // Delete a restaurant by its ID
    ResponseEntity<Void> deleteRestaurantById(Integer restaurantId);
}
