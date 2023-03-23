package dev.drew.restaurantreview.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant toRestaurant(RestaurantEntity restaurantEntity);
    RestaurantEntity toRestaurantEntity(RestaurantInput restaurantInput);
    RestaurantInput toRestaurantInput(RestaurantEntity restaurantEntity);
}