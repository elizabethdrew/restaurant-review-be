package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
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

    // Instance variables for the RestaurantRepository, ReviewRepository, RestaurantMapper, and EntityUserIdProvider
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantMapper restaurantMapper;
    private final EntityUserIdProvider<RestaurantEntity> restaurantUserIdProvider = RestaurantEntity::getUserId;
    private UserRepository userRepository;

    // Constructor with required dependencies
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    private RestaurantResponse createErrorResponse(String message, Throwable cause) {
        RestaurantResponse errorResponse = new RestaurantResponse();
        errorResponse.setSuccess(false);
        errorResponse.setError(new Error().message(message + (cause != null ? ": " + cause.getMessage() : "")));
        return errorResponse;
    }

    public RestaurantResponse addNewRestaurant(RestaurantInput restaurantInput) {
        // Convert the input data to a RestaurantEntity object and set the created timestamp
        RestaurantEntity restaurant = restaurantMapper.toRestaurantEntity(restaurantInput);
        restaurant.setCreatedAt(OffsetDateTime.now());

        // Fetch the current user entity
        Long currentUserId = getCurrentUserId();
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Set the current user to the restaurant
        restaurant.setUser(currentUser);

        // Initialize the RestaurantResponse object
        RestaurantResponse restaurantResponse = new RestaurantResponse();

        try {
            // Save the new restaurant to the database
            RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
            // Convert the saved RestaurantEntity object to a Restaurant object
            Restaurant savedApiRestaurant = restaurantMapper.toRestaurant(savedRestaurant);
            // Set the response fields
            restaurantResponse.setRestaurant(savedApiRestaurant);
            restaurantResponse.setSuccess(true);
            // Return the response
            return restaurantResponse;
        } catch (DataIntegrityViolationException e) {
            // Handle database constraint violations, such as unique constraints
            return createErrorResponse("Invalid input", e);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            return createErrorResponse("Database error", e);
        } catch (Exception e) {
            // Handle general exceptions
            return createErrorResponse("An unexpected error occurred", e);
        }
    }

    // Get all restaurants from the database
    public ResponseEntity<List<Restaurant>> getAllRestaurants(String city, Integer rating, Long userId) {

        // Get a stream of all restaurants and apply filters if provided
        Stream<RestaurantEntity> filteredEntities = restaurantRepository.findAll().stream();

        if (city != null) {
            filteredEntities = filteredEntities.filter(r -> r.getCity().equalsIgnoreCase(city));
        }

        if (rating != null) {
            filteredEntities = filteredEntities
                    .filter(r -> rating.equals(r.getRating()));
        }

        if (userId != null) {
            filteredEntities = filteredEntities.filter(r -> r.getUser().getId().equals(userId));
        }

        // Convert the filtered RestaurantEntity objects to Restaurant objects and return them as a list
        List<Restaurant> restaurants = filteredEntities
                .map(restaurantMapper::toRestaurant)
                .collect(Collectors.toList());
        return ResponseEntity.ok(restaurants);
    }

    // Get a restaurant by ID
    public ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId) {
        // Retrieve the restaurant with the specified ID from the repository
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        // If the restaurant exists, convert it to a Restaurant object and return it
        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = restaurantMapper.toRestaurant(restaurantEntityOptional.get());
            return ResponseEntity.ok(restaurant);
        } else {
            // If the restaurant does not exist, return a NOT_FOUND status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update a restaurant by ID
    public ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput) {
        // Retrieve the restaurant with the specified ID from the repository
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        // If the restaurant exists, proceed with the update
        if (restaurantEntityOptional.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();

            // Check if the current user is an admin or the owner of the restaurant
            if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Convert the input data to a RestaurantEntity object and set its ID, created timestamp, and user ID
            RestaurantEntity updatedEntity = restaurantMapper.toRestaurantEntity(restaurantInput);
            updatedEntity.setId(restaurantEntity.getId());
            updatedEntity.setCreatedAt(restaurantEntity.getCreatedAt());
            updatedEntity.setUser(restaurantEntity.getUser());
            updatedEntity.setRating(restaurantEntity.getRating());

            try {
                // Save the updated restaurant to the database
                RestaurantEntity savedRestaurant = restaurantRepository.save(updatedEntity);
                // Convert the saved RestaurantEntity object to a Restaurant object
                Restaurant savedApiRestaurant = restaurantMapper.toRestaurant(savedRestaurant);
                // Return the updated restaurant
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
            // If the restaurant does not exist, return a NOT_FOUND status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a restaurant by ID
    public ResponseEntity<Void> deleteRestaurantById(Integer restaurantId) {
        // Retrieve the restaurant with the specified ID from the repository
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

        // If the restaurant exists, proceed with the deletion
        if (restaurantEntityOptional.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();

            // Check if the current user is an admin or the owner of the restaurant
            if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Delete the restaurant by ID
            restaurantRepository.deleteById(restaurantId.longValue());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            // If the restaurant does not exist, return a NOT_FOUND status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
