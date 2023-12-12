package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.*;
import dev.drew.restaurantreview.exception.*;
import dev.drew.restaurantreview.mapper.ClaimMapper;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.*;
import dev.drew.restaurantreview.repository.specification.RestaurantSpecification;
import dev.drew.restaurantreview.service.FileStorageService;
import dev.drew.restaurantreview.service.RestaurantService;
import dev.drew.restaurantreview.util.HelperUtils;
import dev.drew.restaurantreview.util.SecurityUtils;
import dev.drew.restaurantreview.util.interfaces.EntityOwnerIdProvider;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.ClaimInput;
import org.openapitools.model.ClaimStatus;
import org.openapitools.model.PaginatedRestaurantResponse;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    // Instance variables for the RestaurantRepository, ReviewRepository, RestaurantMapper, and EntityUserIdProvider
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    private final ClaimRepository claimRepository;
    private final RestaurantMapper restaurantMapper;
    private final ClaimMapper claimMapper;
    private final EntityOwnerIdProvider<RestaurantEntity> restaurantOwnerIdProvider = RestaurantEntity::getOwnerId;
    private UserRepository userRepository;
    private CuisineRepository cuisineRepository;
    private FavouriteRepository favouriteRepository;

    private final FileStorageService fileStorageService;
    private final HelperUtils helperUtils;

    // Constructor with required dependencies
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, ReviewRepository reviewRepository, ClaimRepository claimRepository, ClaimMapper claimMapper, UserRepository userRepository, CuisineRepository cuisineRepository, FavouriteRepository favouriteRepository, FileStorageService fileStorageService, HelperUtils helperUtils) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.reviewRepository = reviewRepository;
        this.claimRepository = claimRepository;
        this.claimMapper = claimMapper;
        this.userRepository = userRepository;
        this.cuisineRepository = cuisineRepository;
        this.favouriteRepository = favouriteRepository;
        this.fileStorageService = fileStorageService;
        this.helperUtils = helperUtils;
    }

    public void validateRestaurantInput(RestaurantInput restaurantInput) {
        log.info("Starting: Validate Restaurant Input...");

        if (restaurantInput.getPriceRange() != null && restaurantInput.getPriceRange() < 1 || restaurantInput.getPriceRange() > 3) {
            throw new InvalidInputException("Price Range not 1, 2 or 3");
        }

        log.info("Input Validated");
    }

    public Restaurant addNewRestaurant(RestaurantInput restaurantInput) {

        log.info("Starting: Add Restaurant...");

        validateRestaurantInput(restaurantInput);

        // Check if the restaurant already exists (including soft-deleted ones)
        Optional<RestaurantEntity> existingRestaurant = restaurantRepository.findByNameAndCity(
                restaurantInput.getName(),
                restaurantInput.getCity()
        );

        if (existingRestaurant.isPresent()) {
            log.info("Restaurant Exists With Name " + restaurantInput.getName() + " in " + restaurantInput.getCity());
            throw new DuplicateRestaurantException(restaurantInput.getName(), restaurantInput.getCity());
        }

        // Handle cuisines
        List<CuisineEntity> cuisines = getCuisines(restaurantInput.getCuisines());

        // Convert the input data to a RestaurantEntity object and set the created timestamp
        log.info("Creating Restaurant...");
        RestaurantEntity restaurant = restaurantMapper.toRestaurantEntity(restaurantInput);
        restaurant.setCreatedAt(OffsetDateTime.now());
        restaurant.setRestaurantCuisines(cuisines);

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();

        // Set the current user to the restaurant
        restaurant.setUser(currentUser);

        // Save the new restaurant to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
        log.info("Restaurant created");

        // Return the response
        return restaurantMapper.toRestaurant(savedRestaurant);
    }

    public List<CuisineEntity> getCuisines(List<String> cuisineNames) {
        log.info("Starting: Get Cuisines...");
        return cuisineNames.stream()
                .map(name -> cuisineRepository.findByName(name)
                        .orElseThrow(() -> new CuisineNotFoundException(name)))
                .collect(Collectors.toList());
    }

    private List<String> getCuisineNames(List<CuisineEntity> cuisines) {
        log.info("Starting: Get Cuisine Names...");
        return cuisines.stream()
                .map(CuisineEntity::getName)
                .collect(Collectors.toList());
    }


    private Page<RestaurantEntity> findRestaurants(
            List<String> city, List<Integer> rating, Long ownerId, List<Integer> priceRange,
            List<String> cuisine, Boolean favouritesOnly, Pageable pageable) {

        log.info("Finding Restaurants");
        // Common specification building logic
        Specification<RestaurantEntity> specs = Specification.where(RestaurantSpecification.isNotDeleted())
                .and(RestaurantSpecification.hasCity(city))
                .and(RestaurantSpecification.hasRating(rating))
                .and(RestaurantSpecification.hasOwnerId(ownerId))
                .and(RestaurantSpecification.hasPriceRange(priceRange))
                .and(RestaurantSpecification.hasCuisine(cuisine));

        // Common favorite filter logic
        UserEntity currentUser = SecurityUtils.getCurrentUser();
        if (currentUser != null && favouritesOnly) {
            specs = specs.and(RestaurantSpecification.isFavouritedByUser(currentUser.getId()));
        }

        // Return the page of entities
        return restaurantRepository.findAll(specs, pageable);
    }

    private Restaurant mapToRestaurantDto(RestaurantEntity entity) {
        log.info("Mapping Restaurants to output");
        Restaurant restaurant = restaurantMapper.toRestaurant(entity);
        Long favouritesCount = favouriteRepository.countByRestaurant(entity);
        restaurant.setTotalFavourites(favouritesCount);
        UserEntity currentUser = SecurityUtils.getCurrentUser();
        if (currentUser != null) {
            Optional<FavouriteEntity> favourite = favouriteRepository.findByRestaurantAndUser(entity, currentUser);
            restaurant.setIsFavourite(favourite.isPresent());
        }
        return restaurant;
    }

    public List<Restaurant> getAllRestaurantsV1(
            List<String> city,
            List<Integer> rating,
            Long ownerId,
            List<Integer> price_range,
            List<String> cuisine,
            Boolean favouritesOnly,
            Pageable pageable) {

        log.info("Starting: Get All Restaurants...");

        Page<RestaurantEntity> filteredEntities = findRestaurants(city, rating, ownerId, price_range, cuisine, favouritesOnly, pageable);

        log.info("Restaurants Incoming!");

        return filteredEntities.getContent().stream()
                .map(this::mapToRestaurantDto)
                .collect(Collectors.toList());
    }

    public PaginatedRestaurantResponse getAllRestaurantsV2(List<String> city, List<Integer> rating, Long ownerId, List<Integer> price_range, List<String> cuisine, Boolean favouritesOnly, Pageable pageable) {

        log.info("Starting: Get All Restaurants...");

        Page<RestaurantEntity> filteredEntities = findRestaurants(city, rating, ownerId, price_range, cuisine, favouritesOnly, pageable);

        log.info("Restaurants Incoming!");
        List<Restaurant> restaurantList = filteredEntities.getContent().stream()
                .map(this::mapToRestaurantDto)
                .collect(Collectors.toList());

        log.info("Creating response object");
        PaginatedRestaurantResponse response = new PaginatedRestaurantResponse();
        response.setTotal(filteredEntities.getTotalElements());
        response.setItems(new ArrayList<>(restaurantList));

        return response;
    }


    public Restaurant getRestaurantById(Integer restaurantId) throws RestaurantNotFoundException {

        log.info("Starting: Get Restaurant...");

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        boolean isFavourited = false;
        Long favouritesCount = favouriteRepository.countByRestaurant(restaurantEntity);

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();

        if (currentUser != null) {
            Optional<FavouriteEntity> favourite = favouriteRepository.findByRestaurantAndUser(restaurantEntity, currentUser);
            isFavourited = favourite.isPresent();
        }

        Restaurant restaurant = restaurantMapper.toRestaurant(restaurantEntity);
        restaurant.setIsFavourite(isFavourited);
        restaurant.setTotalFavourites(favouritesCount);

        return restaurant;
    }


    public Restaurant updateRestaurantById(Integer restaurantId, RestaurantInput restaurantInput)
            throws RestaurantNotFoundException, InsufficientPermissionException {

        log.info("Starting: Update Restaurant...");

        validateRestaurantInput(restaurantInput);

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Check if the current user is an admin or the owner of the restaurant
        if (!SecurityUtils.isAdminOrOwner(restaurantEntity, restaurantOwnerIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this restaurant");
        }

        // Handle cuisines
        List<CuisineEntity> cuisines = getCuisines(restaurantInput.getCuisines());

        // Convert the input data to a RestaurantEntity object and set its ID, created timestamp, and user ID
        log.info("Updating Restaurant...");
        RestaurantEntity updatedEntity = restaurantMapper.toRestaurantEntity(restaurantInput);
        updatedEntity.setId(restaurantEntity.getId());
        updatedEntity.setCreatedAt(restaurantEntity.getCreatedAt());
        updatedEntity.setUser(restaurantEntity.getUser());
        updatedEntity.setRating(restaurantEntity.getRating());
        updatedEntity.setRestaurantCuisines(restaurantEntity.getRestaurantCuisines());

        // Save the updated restaurant to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(updatedEntity);
        log.info("Updated Restaurant Saved");

        // Convert the saved RestaurantEntity object to a Restaurant object
        return restaurantMapper.toRestaurant(savedRestaurant);
    }


    public void deleteRestaurantById(Integer restaurantId) {

        log.info("Starting: Delete Restaurant...");

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        if (!SecurityUtils.isAdminOrOwner(restaurantEntity, restaurantOwnerIdProvider)) {
            log.info("Insufficient User Permissions");
            throw new InsufficientPermissionException("User does not have permission to delete this restaurant");
        }

        // Instead of deleting, we mark the restaurant as deleted
        restaurantEntity.setIsDeleted(true);
        log.info("Restaurant Marked Deleted");
        restaurantRepository.save(restaurantEntity);
        log.info("Restaurant Updated");
    }


    @Transactional
    public boolean toggleFavourite(Integer restaurantId) throws InsufficientPermissionException {

        log.info("Starting: Favourite Restaurant...");

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();

        // Check if the user has already favourited this restaurant
        Optional<FavouriteEntity> favouriteOpt = favouriteRepository.findByRestaurantAndUser(restaurantEntity, currentUser);

        if (favouriteOpt.isPresent()) {
            // If found, we remove it from favourites
            log.info("Restaurant Already Favourite");
            favouriteRepository.delete(favouriteOpt.get());
            log.info("Restaurant Favourite Removed");
            return false;
        } else {
            // Otherwise, add it to favourites
            log.info("Restaurant Not Previously Favourite");
            FavouriteEntity favourite = new FavouriteEntity();
            favourite.setRestaurant(restaurantEntity);
            favourite.setUser(currentUser);
            favouriteRepository.save(favourite);
            log.info("Restaurant Favourite Added");
            return true;
        }

    }

    public ClaimStatus getClaimStatus(Integer restaurantId) {

        log.info(" Starting: Get Claim Status...");

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();

        // Fetch the claim for current user and restaurant
        ClaimEntity claimEntity = claimRepository.findByRestaurantAndClaimant(restaurantEntity, currentUser)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found"));

        // Return the claim status
        log.info("Claim Status Found");
        return claimMapper.toClaim(claimEntity);
    }

    @Transactional
    public ResponseEntity<ClaimStatus> createClaim(Integer restaurantId, ClaimInput claimInput) {

        log.info(" Starting: Create Claim...");

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Check if restaurant already has owner
        if (restaurantEntity.getOwner() != null) {
            log.info(" Restaurant Already Owned");
            throw new RestaurantOwnedException("Restaurant with id " + restaurantId + " is already owned");
        }

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();

        // Check if claim already exists for user and restaurant - return current status if it does
        Optional<ClaimEntity> claimEntity = claimRepository.findByRestaurantAndClaimant(restaurantEntity, currentUser);
        if (claimEntity.isPresent()) {
            log.info("Claim Already Exists");
            return ResponseEntity.ok(claimMapper.toClaim(claimEntity.get()));
        }

        // Convert the input data to a ClaimEntity object
        log.info("Creating Claim");
        ClaimEntity claim = claimMapper.toClaimEntity(claimInput);
        claim.setCreatedAt(OffsetDateTime.now().toLocalDateTime());
        claim.setRestaurant(restaurantEntity);
        claim.setClaimant(currentUser);
        claim.setStatus(ClaimEntity.ClaimStatus.PENDING);
        claim.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());

        // Save the new claim to the database
        ClaimEntity savedClaim = claimRepository.save(claim);
        log.info("Claim Saved");

        // Return the response
        return ResponseEntity.status(HttpStatus.CREATED).body(claimMapper.toClaim(savedClaim));
    }

    @Override
    public Restaurant uploadRestaurantPicture(Integer restaurantId, MultipartFile file) {

        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Check if the current user is an admin or the owner of the restaurant
        if (!SecurityUtils.isAdminOrOwner(restaurantEntity, restaurantOwnerIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this restaurant");
        }

        log.info("Uploading Image");
        String fileUrl = fileStorageService.uploadFile("restaurant-image", restaurantId, file);

        log.info("Updating User");
        restaurantEntity.setImageUrl(fileUrl);
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.toRestaurant(savedRestaurant);
    }


}
