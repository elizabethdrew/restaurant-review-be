package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.openapitools.model.Error;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.drew.restaurantreview.util.SecurityUtils.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantMapper restaurantMapper;
    private final EntityUserIdProvider<RestaurantEntity> restaurantUserIdProvider = RestaurantEntity::getUserId;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.reviewRepository = reviewRepository;
    }

    // Add a new restaurant to the database
    public ResponseEntity<RestaurantResponse> addNewRestaurant(RestaurantInput restaurantInput) {

        RestaurantEntity restaurant = restaurantMapper.toRestaurantEntity(restaurantInput);
        restaurant.setCreatedAt(OffsetDateTime.now());
        Long currentUserId = getCurrentUserId();
        restaurant.setUserId(currentUserId);

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        try {
            RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
            Restaurant savedApiRestaurant = restaurantMapper.toRestaurant(savedRestaurant);
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


    // Get all restaurants from the database
    public ResponseEntity<List<Restaurant>> getAllRestaurants(String city, Integer rating, Long userId) {

        Stream<RestaurantEntity> filteredEntities = restaurantRepository.findAll().stream();

        if (city != null) {
            filteredEntities = filteredEntities.filter(r -> r.getCity().equalsIgnoreCase(city));
        }

        if (rating != null) {
            filteredEntities = filteredEntities.filter(r -> r.getRating().equals(rating));
        }

        if (userId != null) {
            filteredEntities = filteredEntities.filter(r -> r.getUserId().equals(userId));
        }

        List<Restaurant> restaurants = filteredEntities
                .map(restaurantMapper::toRestaurant)
                .collect(Collectors.toList());
        return ResponseEntity.ok(restaurants);
    }


    // Get a restaurant by ID
    public ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = restaurantMapper.toRestaurant(restaurantEntityOptional.get());
            return ResponseEntity.ok(restaurant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Update a restaurant by ID
    public ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();

            // Check if the current user is an admin or the owner of the restaurant
            if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            RestaurantEntity updatedEntity = restaurantMapper.toRestaurantEntity(restaurantInput);
            updatedEntity.setId(restaurantEntity.getId());
            updatedEntity.setCreatedAt(restaurantEntity.getCreatedAt());
            updatedEntity.setUserId(restaurantEntity.getUserId());

            try {
                RestaurantEntity savedRestaurant = restaurantRepository.save(updatedEntity);
                Restaurant savedApiRestaurant = restaurantMapper.toRestaurant(savedRestaurant);
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


    // Delete a restaurant by ID
    public ResponseEntity<Void> deleteRestaurantById(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        if (restaurantEntityOptional.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();

            // Check if the current user is an admin or the owner of the restaurant
            if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            restaurantRepository.deleteById(restaurantId.longValue());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
