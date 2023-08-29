package dev.drew.restaurantreview.service;

import org.openapitools.model.Restaurant;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    List<Restaurant> searchRestaurant(String query, Pageable pageable);
}