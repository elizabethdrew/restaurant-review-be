package dev.drew.restaurantreview.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.openapitools.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public abstract class RestaurantMapperDecorator implements RestaurantMapper {

    @Autowired
    private RestaurantMapper delegate;

    @Override
    public Restaurant toRestaurant(RestaurantEntity restaurantEntity) {
        Restaurant restaurant = delegate.toRestaurant(restaurantEntity);
        if (restaurantEntity.getRestaurantCuisines() != null) {
            restaurant.setCuisines(restaurantEntity.getRestaurantCuisines().stream()
                    .map(cuisine -> cuisine.getName())
                    .collect(Collectors.toList()));
        }
        return restaurant;
    }
}
