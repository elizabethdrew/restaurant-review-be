package org.example.controller;

import org.example.model.RestaurantModel;
import org.example.repository.RestaurantRepository;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.api.RestaurantsApi;

import org.openapitools.client.model.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {
    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() throws ApiException {
        List<Restaurant> restaurants = this.restaurantRepository.findAll();
        return restaurants;
    }

}