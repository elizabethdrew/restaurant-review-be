package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.data.domain.Pageable;

import java.util.List;

// RestaurantService is an interface for managing restaurants.
public interface RestaurantService {

    // Add a new restaurant
    Restaurant addNewRestaurant(RestaurantInput restaurantInput);

    // Get all restaurants with optional filters (city, rating, and userId)
    List<Restaurant> getAllRestaurants(List<String> city, List<Integer> rating, Long userId, List<Integer> price_range, List<String> cuisine, Pageable pageable);

    // Get a restaurant by its ID
    Restaurant getRestaurantById(Integer restaurantId) throws RestaurantNotFoundException;

    // Update a restaurant by its ID
    Restaurant updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException;

    // Delete a restaurant by its ID
    void deleteRestaurantById(Integer restaurantId);

    boolean toggleFavourite(Integer restaurantId);
}
