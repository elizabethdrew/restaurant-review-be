package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.CuisineEntity;

import java.util.List;

public interface CuisineService {

    List<String> getAllCuisineNames();

    CuisineEntity addCuisine(String name);

    void deleteCuisine(String name);

}
