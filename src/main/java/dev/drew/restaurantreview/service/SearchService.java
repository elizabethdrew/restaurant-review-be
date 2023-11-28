package dev.drew.restaurantreview.service;

import org.openapitools.model.PaginatedRestaurantResponse;
import org.openapitools.model.Restaurant;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    List<Restaurant> searchRestaurantV1(String query, Pageable pageable);

    PaginatedRestaurantResponse searchRestaurantV2(String query, Pageable pageable);
}