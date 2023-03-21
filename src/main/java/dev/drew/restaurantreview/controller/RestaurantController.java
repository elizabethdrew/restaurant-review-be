package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.entity.Restaurant;
import dev.drew.restaurantreview.repository.RestaurantRepository;

import org.openapitools.model.Error;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
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
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setCity(restaurantInput.getCity());
        restaurant.setRating(restaurantInput.getRating());
        restaurant.setCreatedAt(OffsetDateTime.now());

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        try {
            Restaurant savedRestaurant = restaurantRepository.save(restaurant);
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
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
