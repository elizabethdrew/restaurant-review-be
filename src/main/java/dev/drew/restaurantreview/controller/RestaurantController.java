package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.repository.RestaurantRepository;

import jakarta.validation.Valid;
import org.openapitools.model.Error;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class RestaurantController implements org.openapitools.api.RestaurantsApi {

    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput) {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(restaurantInput.getName());
        restaurant.setCity(restaurantInput.getCity());
        restaurant.setRating(restaurantInput.getRating());
        restaurant.setCreatedAt(OffsetDateTime.now());

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        try {
            RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
            restaurantResponse.setRestaurant(savedRestaurant);
            restaurantResponse.setSuccess(true);
            return new ResponseEntity<>(restaurantResponse, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle database constraint violations, such as unique constraints
            restaurantResponse.setSuccess(false);
            restaurantResponse.setError(new Error().message("Invalid input: " + e.getMessage()));
            return new ResponseEntity<>(restaurantResponse, HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            restaurantResponse.setSuccess(false);
            restaurantResponse.setError(new Error().message("Database error: " + e.getMessage()));
            return new ResponseEntity<>(restaurantResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle general exceptions
            restaurantResponse.setSuccess(false);
            restaurantResponse.setError(new Error().message("An unexpected error occurred: " + e.getMessage()));
            return new ResponseEntity<>(restaurantResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = (List<Restaurant>) (List<?>) restaurantRepository.findAll();
        return ResponseEntity.ok(restaurants);
    }

    @Override
    public ResponseEntity<Restaurant> restaurantsRestaurantIdGet(@PathVariable("restaurantId") Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = (Restaurant) restaurantEntityOptional.get();
            return ResponseEntity.ok(restaurant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Override
    public ResponseEntity<Restaurant> restaurantsRestaurantIdPut(Integer restaurantId, RestaurantInput restaurantInput) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = (Restaurant) restaurantEntityOptional.get();
            restaurant.setName(restaurantInput.getName());
            restaurant.setCity(restaurantInput.getCity());
            restaurant.setRating(restaurantInput.getRating());
            return ResponseEntity.ok(restaurant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
