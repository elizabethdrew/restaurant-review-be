package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.dto.SearchDTO;
import org.openapitools.model.Restaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SearchService {
    List<Restaurant> searchRestaurant(String query);
}