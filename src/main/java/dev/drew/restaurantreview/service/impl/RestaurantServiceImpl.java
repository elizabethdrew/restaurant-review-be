package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.service.RestaurantService;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.drew.restaurantreview.util.SecurityUtils.*;

@Slf4j
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

    public Restaurant addNewRestaurant(RestaurantInput restaurantInput) {
        // Convert the input data to a RestaurantEntity object and set the created timestamp
        RestaurantEntity restaurant = restaurantMapper.toRestaurantEntity(restaurantInput);
        restaurant.setCreatedAt(OffsetDateTime.now());

        // Fetch the current user entity
        Long currentUserId = getCurrentUserId();
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Set the current user to the restaurant
        restaurant.setUser(currentUser);

        // Save the new restaurant to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);

        // Convert the saved RestaurantEntity object to a Restaurant object

        // Return the response
        return restaurantMapper.toRestaurant(savedRestaurant);
    }

    public List<Restaurant> getAllRestaurants(String city, Integer rating, Long userId) {
        // Get a stream of all restaurants and apply filters if provided
        Stream<RestaurantEntity> filteredEntities = restaurantRepository.findAll().stream();

        if (city != null) {
            filteredEntities = filteredEntities.filter(r -> r.getCity().equalsIgnoreCase(city));
            if(filteredEntities.count() <= 0)
            throw new RestaurantNotFoundException("Unable to match any records matching city name.");
        }

        if (rating != null) {
            filteredEntities = filteredEntities.filter(r -> rating.equals(r.getRating()));
            if(filteredEntities.count() <= 0)
            throw new RestaurantNotFoundException("Unable to match any records matching ratings.");
        }

        if (userId != null) {
            filteredEntities = filteredEntities.filter(r -> r.getUser().getId().equals(userId));
            if(filteredEntities.count() <= 0)
            throw new RestaurantNotFoundException("Unable to match any records matching userId.");
        }

        // Convert the filtered RestaurantEntity objects to Restaurant objects and return them as a list
        return filteredEntities.map(restaurantMapper::toRestaurant).collect(Collectors.toList());
    }


    // Get a restaurant by ID
    public Restaurant getRestaurantById(Integer restaurantId) throws RestaurantNotFoundException {
        // Retrieve the restaurant with the specified ID from the repository
        return restaurantRepository.findById(restaurantId.longValue())
                .map(restaurantMapper::toRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId));
    }

    public Restaurant updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException {
        // Retrieve the restaurant with the specified ID from the repository
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId.longValue())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId));

        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this restaurant");
        }

        // Convert the input data to a RestaurantEntity object and set its ID, created timestamp, and user ID
        RestaurantEntity updatedEntity = restaurantMapper.toRestaurantEntity(restaurantInput);
        updatedEntity.setId(restaurantEntity.getId());
        updatedEntity.setCreatedAt(restaurantEntity.getCreatedAt());
        updatedEntity.setUser(restaurantEntity.getUser());
        updatedEntity.setRating(restaurantEntity.getRating());

        // Save the updated restaurant to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(updatedEntity);

        // Convert the saved RestaurantEntity object to a Restaurant object
        return restaurantMapper.toRestaurant(savedRestaurant);
    }

    // Delete a restaurant by ID
    public void deleteRestaurantById(Integer restaurantId) {
        // Retrieve the restaurant with the specified ID from the repository
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId.longValue())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to delete this restaurant");
        }

        // Delete the restaurant by ID
        restaurantRepository.deleteById(restaurantId.longValue());
    }

}
