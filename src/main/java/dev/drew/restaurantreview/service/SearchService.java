package dev.drew.restaurantreview.service;

import org.openapitools.model.Restaurant;

import java.util.List;

public interface SearchService {
    List<Restaurant> searchRestaurant(String query);
}