package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import org.openapitools.model.ClaimInput;
import org.openapitools.model.ClaimStatus;
import org.openapitools.model.PaginatedRestaurantResponse;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// RestaurantService is an interface for managing restaurants.
public interface RestaurantService {

    // Add a new restaurant
    Restaurant addNewRestaurant(RestaurantInput restaurantInput);

    // Get all restaurants with optional filters (city, rating, and userId)
    List<Restaurant> getAllRestaurantsV1(List<String> city, List<Integer> rating, Long ownerId, List<Integer> price_range, List<String> cuisine, Boolean favouritesOnly, Pageable pageable);
    PaginatedRestaurantResponse getAllRestaurantsV2(List<String> city, List<Integer> rating, Long ownerId, List<Integer> price_range, List<String> cuisine, Boolean favouritesOnly, Pageable pageable);

    // Get a restaurant by its ID
    Restaurant getRestaurantById(Integer restaurantId) throws RestaurantNotFoundException;

    // Update a restaurant by its ID
    Restaurant updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException;

    // Delete a restaurant by its ID
    void deleteRestaurantById(Integer restaurantId);

    boolean toggleFavourite(Integer restaurantId);

    ClaimStatus getClaimStatus(Integer restaurantId);
    ResponseEntity<ClaimStatus> createClaim(Integer restaurantId, ClaimInput claimInput);

    Restaurant uploadRestaurantPicture(Integer restaurantId, MultipartFile file);

}
