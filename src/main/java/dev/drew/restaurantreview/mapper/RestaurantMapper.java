package dev.drew.restaurantreview.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.mapstruct.Mapping;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;

// The RestaurantMapper interface defines methods for mapping between RestaurantEntity, Restaurant, and RestaurantInput classes.
@Mapper(componentModel = "spring")
@DecoratedWith(RestaurantMapperDecorator.class)
public interface RestaurantMapper {

    // Method to convert a RestaurantEntity object to a Restaurant object
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "user.id", target = "userId")
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    // Method to convert a RestaurantInput object to a RestaurantEntity object
    RestaurantEntity toRestaurantEntity(RestaurantInput restaurantInput);

    // Method to convert a RestaurantEntity object to a RestaurantInput object
    RestaurantInput toRestaurantInput(RestaurantEntity restaurantEntity);

}