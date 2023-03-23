package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;

import org.openapitools.model.Error;
import org.openapitools.model.Restaurant;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class RestaurantController implements org.openapitools.api.RestaurantsApi {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /*
    Add a new restaurant to the database
    Example curl command: curl -X POST http://localhost:8080/restaurants -H "Content-Type: application/json" -d '{"name": "Restaurant Name", "city": "City Name", "rating": 4}'
*/
    @Override
    public ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput) {
        RestaurantMapper mapper = RestaurantMapper.INSTANCE;
        RestaurantEntity restaurant = mapper.toRestaurantEntity(restaurantInput);
        restaurant.setCreatedAt(OffsetDateTime.now());

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        try {
            RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
            Restaurant savedApiRestaurant = mapper.toRestaurant(savedRestaurant);
            restaurantResponse.setRestaurant(savedApiRestaurant);
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

    /*
    Get all restaurants from the database
    Example curl command: curl -X GET http://localhost:8080/restaurants
    */
    @Override
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        List<Restaurant> restaurants = restaurantEntities.stream()
                .map(RestaurantMapper.INSTANCE::toRestaurant)
                .collect(Collectors.toList());
        return ResponseEntity.ok(restaurants);
    }

    /*
    Get a restaurant by ID
    Example curl command: curl -X GET http://localhost:8080/restaurants/{restaurantId}
    */
    @Override
    public ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = RestaurantMapper.INSTANCE.toRestaurant(restaurantEntityOptional.get());
            return ResponseEntity.ok(restaurant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    Update a restaurant by ID
    Example curl command: curl -X PUT http://localhost:8080/restaurants/{restaurantId} -H "Content-Type: application/json" -d '{"name": "Updated Name", "city": "Updated City", "rating": 5}'
*/
    @Override
    public ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput) {
        RestaurantMapper mapper = RestaurantMapper.INSTANCE;
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();
            RestaurantEntity updatedEntity = mapper.toRestaurantEntity(restaurantInput);
            updatedEntity.setId(restaurantEntity.getId());
            updatedEntity.setCreatedAt(restaurantEntity.getCreatedAt());

            try {
                RestaurantEntity savedRestaurant = restaurantRepository.save(updatedEntity);
                Restaurant savedApiRestaurant = mapper.toRestaurant(savedRestaurant);
                return ResponseEntity.ok(savedApiRestaurant);
            } catch (DataIntegrityViolationException e) {
                // Handle database constraint violations, such as unique constraints
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (DataAccessException e) {
                // Handle other database-related exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } catch (Exception e) {
                // Handle general exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    Delete a restaurant by ID
    Example curl command: curl -X DELETE http://localhost:8080/restaurants/{restaurantId}
    */
    @Override
    public ResponseEntity<Void> deleteRestaurantById(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());
        if (restaurantEntityOptional.isPresent()) {
            restaurantRepository.deleteById(restaurantId.longValue());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
