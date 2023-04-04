package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.RestaurantService;

import org.openapitools.api.RestaurantsApi;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
public class RestaurantController implements RestaurantsApi {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput) {
        return restaurantService.addNewRestaurant(restaurantInput);
    }

    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @Valid @RequestParam(value = "city", required = false) String city,
            @Min(1) @Max(5) @Valid @RequestParam(value = "rating", required = false) Integer rating,
            @Valid @RequestParam(value = "user_id", required = false) Long userId) {
        return restaurantService.getAllRestaurants(city, rating, userId);
    }

    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @Override
    public ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput) {
        return restaurantService.updateRestaurantById(restaurantId, restaurantInput);
    }

    @Override
    public ResponseEntity<Void> deleteRestaurantById(Integer restaurantId) {
        return restaurantService.deleteRestaurantById(restaurantId);
    }
}
