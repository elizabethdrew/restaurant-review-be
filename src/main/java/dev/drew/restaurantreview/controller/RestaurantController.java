package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.entity.Restaurant;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class RestaurantController implements org.openapitools.api.RestaurantsApi {

    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput) {
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
