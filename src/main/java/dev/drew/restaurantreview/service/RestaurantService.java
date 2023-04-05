package dev.drew.restaurantreview.service;

import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput);
    ResponseEntity<List<Restaurant>> getAllRestaurants(String city, Integer rating, Long userId);
    ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId);
    ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput);
    ResponseEntity<Void> deleteRestaurantById(Integer restaurantId);
}
