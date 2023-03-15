package org.example.mapper;

import org.example.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.openapitools.client.model.Restaurant;

@Mapper
public interface RestaurantMapper {
    RestaurantModel toEntity(Restaurant dto);
    Restaurant toDto(RestaurantModel entity);
}