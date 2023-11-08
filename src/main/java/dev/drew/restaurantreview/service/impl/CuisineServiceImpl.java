package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.CuisineNotFoundException;
import dev.drew.restaurantreview.exception.CuisineReferencedByRestaurantException;
import dev.drew.restaurantreview.exception.DuplicateCuisineException;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.repository.CuisineRepository;
import dev.drew.restaurantreview.service.CuisineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.drew.restaurantreview.util.SecurityUtils.isAdmin;

@Slf4j
@Service
public class CuisineServiceImpl implements CuisineService {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Override
    public List<String> getAllCuisineNames() {
        log.info("Starting: Get All Cuisine Names");
        List<CuisineEntity> cuisines = cuisineRepository.findAll();
        log.info("Cuisines Incoming!");
        return cuisines.stream().map(CuisineEntity::getName).collect(Collectors.toList());
    }

    @Override
    public CuisineEntity addCuisine(String name) {

        log.info("Starting: Add Cuisine");

        // Check if the cuisine already exists
        log.info("Looking For Existing Cuisine");
        Optional<CuisineEntity> existingCuisine = cuisineRepository.findByName(name);

        if (existingCuisine.isPresent()) {
            log.info("Cuisine Already Exists");
            throw new DuplicateCuisineException("A cuisine with the same name already exists");
        }
        log.info("Cuisine Doesn't Exist");
        log.info("Creating Cuisine");
        CuisineEntity cuisine = new CuisineEntity();
        cuisine.setName(name);
        CuisineEntity savedCuisine = cuisineRepository.save(cuisine);
        log.info("Cuisine Saved");
        return savedCuisine;
    }

    @Override
    public void deleteCuisine(String name) {

        log.info("Starting: Delete Cuisine");

        log.info("Looking For Existing Cuisine");
        Optional<CuisineEntity> cuisine = cuisineRepository.findByName(name);

        // Check if cuisine exists
        CuisineEntity existingCuisine = cuisine.orElseThrow(() -> new CuisineNotFoundException(name));

        // Check if cuisine referenced by restaurant
        log.info("Checking Restaurants");
        List<RestaurantEntity> restaurants = existingCuisine.getRestaurants();
        if (restaurants != null && !restaurants.isEmpty()) {
            log.info("Restaurant References Cuisine");
            throw new CuisineReferencedByRestaurantException(name);
        }
        log.info("No Restaurants Found");

        // Check if the current user is an admin
        if (!isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to delete this cuisine");
        }

        cuisineRepository.delete(existingCuisine);
        log.info("Cuisine Deleted");
    }

}
