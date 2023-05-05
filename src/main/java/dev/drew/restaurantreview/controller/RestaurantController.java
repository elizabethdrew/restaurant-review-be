package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.service.RestaurantService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.openapitools.api.RestaurantsApi;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins ="http://localhost:3000")
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
public class RestaurantController implements RestaurantsApi {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Add a new restaurant to the database.
     *
     * @param restaurantInput input data for the new restaurant
     * @return response entity containing the new restaurant data
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping("/restaurant/add")
    public ResponseEntity<RestaurantResponse> addNewRestaurant(
            @RequestBody @Valid RestaurantInput restaurantInput) {
        RestaurantResponse restaurantResponse = restaurantService.addNewRestaurant(restaurantInput);
        HttpStatus status = restaurantResponse.getError() == null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(restaurantResponse, status);
    }

    /**
     * Get a list of all restaurants in the database.
     *
     * @param city the city to filter by
     * @param rating the minimum rating to filter by
     * @param userId the ID of the user to filter by
     * @return response entity containing the list of restaurants
     */
    @GetMapping("/restaurants")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "rating", required = false)
            @Min(1) @Max(5) Integer rating,
            @RequestParam(value = "user_id", required = false) Long userId) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants(city, rating, userId);
        return ResponseEntity.ok(restaurants);
    }

    /**
     * Get a specific restaurant by ID.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     * @return response entity containing the restaurant data
     */
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Restaurant> getRestaurantById(
            @PathVariable Integer restaurantId) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            return ResponseEntity.ok(restaurant);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    /**
     * Update a restaurant's data by ID.
     *
     * @param restaurantId the ID of the restaurant to update
     * @param restaurantInput the updated restaurant data
     * @return response entity containing the updated restaurant data
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/restaurant/{restaurantId}/edit")
    public ResponseEntity<Restaurant> updateRestaurantById(
            @PathVariable Integer restaurantId,
            @RequestBody @Valid RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantById(restaurantId, restaurantInput);
        return ResponseEntity.ok(updatedRestaurant);
    }

    /**
     * Delete a restaurant from the database by ID.
     *
     * @param restaurantId the ID of the restaurant to delete
     * @return response entity indicating success or failure of the operation
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("restaurant/{restaurantId}/delete")
    public ResponseEntity<Void> deleteRestaurantById(
            @PathVariable Integer restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
