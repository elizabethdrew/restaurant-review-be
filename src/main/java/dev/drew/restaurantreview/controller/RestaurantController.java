package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.service.RestaurantService;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.RestaurantsApi;
import org.openapitools.model.ClaimInput;
import org.openapitools.model.ClaimStatus;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
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
    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @PostMapping
    public ResponseEntity<Restaurant> addNewRestaurant(
            @RequestBody @Valid RestaurantInput restaurantInput) {
        Restaurant restaurant = restaurantService.addNewRestaurant(restaurantInput);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }


    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @Valid @RequestParam(value = "city", required = false) List<String> city,
            @Valid @RequestParam(value = "rating", required = false) List<Integer> rating,
            @Valid @RequestParam(value = "user_id", required = false) Long userId,
            @Valid @RequestParam(value = "price_range", required = false) List<Integer> price_range,
            @Valid @RequestParam(value = "cuisine", required = false) List<String> cuisine,
            @ParameterObject Pageable pageable) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants(city, rating, userId, price_range, cuisine, pageable);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Restaurant> getRestaurantById(
            @Min(1) @PathVariable Integer restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurant);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{restaurantId}/claim")
    public ResponseEntity<ClaimStatus> getRestaurantClaimStatus(@PathVariable("restaurantId") Integer restaurantId) {
        ClaimStatus status = restaurantService.getClaimStatus(restaurantId);
        return ResponseEntity.ok(status);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{restaurantId}/claim")
    public ResponseEntity<ClaimStatus> createRestaurantClaim(@PathVariable Integer restaurantId, ClaimInput claimInput) {
        ClaimStatus status = restaurantService.createClaim(restaurantId, claimInput);
        return ResponseEntity.status(201).body(status);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurantById(
            @Min(1) @PathVariable Integer restaurantId,
            @RequestBody @Valid RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantById(restaurantId, restaurantInput);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurantById(
            @Min(1) @PathVariable Integer restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
