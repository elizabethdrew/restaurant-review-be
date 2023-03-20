package dev.drew.restaurantreview.controller;

import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class NewControoler implements org.openapitools.api.RestaurantsApi {
    @Override
    public ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput) {
        return null;
    }

    @Override
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
