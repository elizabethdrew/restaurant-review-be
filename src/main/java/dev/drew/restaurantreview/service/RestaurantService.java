package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    //Get current user details
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        UserEntity userEntity = securityUser.getUserEntity();
        return userEntity.getId();
    }


    /*
        Add a new restaurant to the database
        Example curl command: curl -X POST http://localhost:8080/restaurants -H "Content-Type: application/json" -d '{"name": "Restaurant Name", "city": "City Name", "rating": 4}'
    */
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

        /*
        Get all restaurants from the database
        Example curl command: curl -X GET http://localhost:8080/restaurants
        */
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

        /*
        Get a restaurant by ID
        Example curl command: curl -X GET http://localhost:8080/restaurants/{restaurantId}
        */

        public ResponseEntity<Restaurant> getRestaurantById(Integer restaurantId) {
            Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

            if (restaurantEntityOptional.isPresent()) {
                Restaurant restaurant = restaurantMapper.toRestaurant(restaurantEntityOptional.get());
                return ResponseEntity.ok(restaurant);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        /*
        Update a restaurant by ID
        Example curl command: curl -X PUT http://localhost:8080/restaurants/{restaurantId} -H "Content-Type: application/json" -d '{"name": "Updated Name", "city": "Updated City", "rating": 5}'
    */
        public ResponseEntity<Restaurant> updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            UserEntity currentUserEntity = securityUser.getUserEntity();

            Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId.longValue());

            if (restaurantEntityOptional.isPresent()) {
                RestaurantEntity restaurantEntity = restaurantEntityOptional.get();

                // Check if the current user is an admin or the owner of the restaurant
                if (!securityUser.hasRole("ROLE_ADMIN") && !currentUserEntity.getId().equals(restaurantEntity.getUserId())) {
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

        /*
        Delete a restaurant by ID
        Example curl command: curl -X DELETE http://localhost:8080/restaurants/{restaurantId}
        */
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
