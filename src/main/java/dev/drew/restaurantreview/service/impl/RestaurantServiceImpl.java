package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.*;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.CuisineRepository;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.repository.specification.RestaurantSpecification;
import dev.drew.restaurantreview.service.RestaurantService;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private CuisineRepository cuisineRepository;

    // Constructor with required dependencies
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, ReviewRepository reviewRepository, UserRepository userRepository, CuisineRepository cuisineRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.cuisineRepository = cuisineRepository;
    }

    public void validateRestaurantInput(RestaurantInput restaurantInput) {

        if(restaurantInput.getPriceRange() != null && restaurantInput.getPriceRange() < 1 || restaurantInput.getPriceRange() > 3) {
            throw new InvalidInputException("Price Range not 1, 2 or 3");
        }

    }

    public Restaurant addNewRestaurant(RestaurantInput restaurantInput) {

        validateRestaurantInput(restaurantInput);

        // Check if the restaurant already exists (including soft-deleted ones)
        Optional<RestaurantEntity> existingRestaurant = restaurantRepository.findByNameAndCity(
                restaurantInput.getName(),
                restaurantInput.getCity()
        );

        if (existingRestaurant.isPresent()) {
            // Even if it's been soft-deleted, we don't want to create a new one with the same name and city
            throw new DuplicateRestaurantException(restaurantInput.getName(), restaurantInput.getCity());
        }

        // Handle cuisines
        List<CuisineEntity> cuisines = getCuisines(restaurantInput.getCuisines());

        // Convert the input data to a RestaurantEntity object and set the created timestamp
        RestaurantEntity restaurant = restaurantMapper.toRestaurantEntity(restaurantInput);
        restaurant.setCreatedAt(OffsetDateTime.now());
        restaurant.setRestaurantCuisines(cuisines);

        // Fetch the current user entity
        Long currentUserId = getCurrentUserId();
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Set the current user to the restaurant
        restaurant.setUser(currentUser);

        // Save the new restaurant to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);

        // Return the response
        return restaurantMapper.toRestaurant(savedRestaurant);
    }

    public List<CuisineEntity> getCuisines(List<String> cuisineNames) {
        return cuisineNames.stream()
                .map(name -> cuisineRepository.findByName(name)
                        .orElseThrow(() -> new CuisineNotFoundException(name)))
                .collect(Collectors.toList());
    }

    private List<String> getCuisineNames(List<CuisineEntity> cuisines) {
        return cuisines.stream()
                .map(CuisineEntity::getName)
                .collect(Collectors.toList());
    }


    public List<Restaurant> getAllRestaurants(List<String> city, List<Integer> rating, Long userId, List<Integer> price_range, List<String> cuisine, Pageable pageable) {
        Page<RestaurantEntity> filteredEntities = restaurantRepository.findAll(
                Specification.where(RestaurantSpecification.isNotDeleted())
                        .and(RestaurantSpecification.hasCity(city))
                        .and(RestaurantSpecification.hasRating(rating))
                        .and(RestaurantSpecification.hasUserId(userId))
                        .and(RestaurantSpecification.hasPriceRange(price_range))
                        .and(RestaurantSpecification.hasCuisine(cuisine)),
                pageable
        );

        return filteredEntities.stream().map(restaurantMapper::toRestaurant).collect(Collectors.toList());
    }



    // Get a restaurant by ID
    public Restaurant getRestaurantById(Integer restaurantId) throws RestaurantNotFoundException {
        // Use a Specification to find a non-deleted restaurant by its ID
        RestaurantEntity restaurantEntity = restaurantRepository.findOne(
                Specification.where(RestaurantSpecification.hasId(restaurantId.longValue()))
                        .and(RestaurantSpecification.isNotDeleted())
        ).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        return restaurantMapper.toRestaurant(restaurantEntity);
    }

    public Restaurant updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException {

        validateRestaurantInput(restaurantInput);

        // Use a Specification to find a non-deleted restaurant by its ID
        RestaurantEntity restaurantEntity = restaurantRepository.findOne(
                Specification.where(RestaurantSpecification.hasId(restaurantId.longValue()))
                        .and(RestaurantSpecification.isNotDeleted())
        ).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));


        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this restaurant");
        }

        // Handle cuisines
        List<CuisineEntity> cuisines = getCuisines(restaurantInput.getCuisines());

        // Convert the input data to a RestaurantEntity object and set its ID, created timestamp, and user ID
        RestaurantEntity updatedEntity = restaurantMapper.toRestaurantEntity(restaurantInput);
        updatedEntity.setId(restaurantEntity.getId());
        updatedEntity.setCreatedAt(restaurantEntity.getCreatedAt());
        updatedEntity.setUser(restaurantEntity.getUser());
        updatedEntity.setRating(restaurantEntity.getRating());
        updatedEntity.setRestaurantCuisines(restaurantEntity.getRestaurantCuisines()); // why isn't this saving???

        // Save the updated restaurant to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(updatedEntity);


        // Convert the saved RestaurantEntity object to a Restaurant object
        return restaurantMapper.toRestaurant(savedRestaurant);
    }

    // Delete a restaurant by ID
    public void deleteRestaurantById(Integer restaurantId) {

        // Use a Specification to find a non-deleted restaurant by its ID
        RestaurantEntity restaurantEntity = restaurantRepository.findOne(
                Specification.where(RestaurantSpecification.hasId(restaurantId.longValue()))
                        .and(RestaurantSpecification.isNotDeleted())
        ).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));


        if (!isAdminOrOwner(restaurantEntity, restaurantUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to delete this restaurant");
        }

        System.out.println(restaurantEntity);

        // Instead of deleting, we mark the restaurant as deleted
        restaurantEntity.setIsDeleted(true);

        System.out.println(restaurantEntity);

        restaurantRepository.save(restaurantEntity);
    }

}
