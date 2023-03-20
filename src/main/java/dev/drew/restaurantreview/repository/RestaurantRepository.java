package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepository {

    List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepository() {
        restaurants.add(new Restaurant(
                1,
                "Food Hall",
                "Bristol",
                5
        ));
    }

    public List<Restaurant> findAll() { return restaurants; }
}
