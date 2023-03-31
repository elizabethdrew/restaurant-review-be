package dev.drew.restaurantreview.mapper;

import org.mapstruct.Mapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant toRestaurant(RestaurantEntity restaurantEntity);
    RestaurantEntity toRestaurantEntity(RestaurantInput restaurantInput);
    RestaurantInput toRestaurantInput(RestaurantEntity restaurantEntity);
}