package dev.drew.restaurantreview.mapper;

import org.mapstruct.Mapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;

// The RestaurantMapper interface defines methods for mapping between RestaurantEntity, Restaurant, and RestaurantInput classes.
@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    // Method to convert a RestaurantEntity object to a Restaurant object
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    // Method to convert a RestaurantInput object to a RestaurantEntity object
    RestaurantEntity toRestaurantEntity(RestaurantInput restaurantInput);

    // Method to convert a RestaurantEntity object to a RestaurantInput object
    RestaurantInput toRestaurantInput(RestaurantEntity restaurantEntity);
}