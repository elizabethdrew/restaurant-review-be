package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.CuisineNotFoundException;
import dev.drew.restaurantreview.exception.CuisineReferencedByRestaurantException;
import dev.drew.restaurantreview.exception.DuplicateCuisineException;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.repository.CuisineRepository;
import dev.drew.restaurantreview.service.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.drew.restaurantreview.util.SecurityUtils.isAdmin;

@Service
public class CuisineServiceImpl implements CuisineService {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Override
    public List<String> getAllCuisineNames() {
        List<CuisineEntity> cuisines = cuisineRepository.findAll();
        return cuisines.stream().map(CuisineEntity::getName).collect(Collectors.toList());
    }

    @Override
    public CuisineEntity addCuisine(String name) {
        // Check if the cuisine already exists
        Optional<CuisineEntity> existingCuisine = cuisineRepository.findByName(name);

        if (existingCuisine.isPresent()) {
            throw new DuplicateCuisineException("A cuisine with the same name already exists");
        }
        CuisineEntity cuisine = new CuisineEntity();
        cuisine.setName(name);
        CuisineEntity savedCuisine = cuisineRepository.save(cuisine);

        return savedCuisine;
    }

    @Override
    public void deleteCuisine(String name) {
        Optional<CuisineEntity> cuisine = cuisineRepository.findByName(name);

        // Check if cuisine exists
        CuisineEntity existingCuisine = cuisine.orElseThrow(() -> new CuisineNotFoundException(name));

        // Check if cuisine referenced by restaurant
        List<RestaurantEntity> restaurants = existingCuisine.getRestaurants();
        if (restaurants != null && !restaurants.isEmpty()) {
            throw new CuisineReferencedByRestaurantException(name);
        }

        // Check if the current user is an admin
        if (!isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to delete this cuisine");
        }
        cuisineRepository.delete(existingCuisine);
    }

}
