package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.RestaurantService;
import org.openapitools.api.RestaurantsApi;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
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
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @Override
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
